package com.jongho.hobbytalk.api.user.command.common.util.token

import com.jongho.hobbytalk.api.common.exception.UnAuthorizedException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Qualifier
import java.security.Key
import java.util.*


class JwtUtil(
    @Qualifier("jwtSecretKey") private val secretKey: String,
    @Qualifier("jwtAlgorithm") private val algorithm: String): TokenUtil {

    override fun createAccessToken(userId: Long): String {
        val expireTime = Date()
        expireTime.setTime(expireTime.time + 1000 * 60 * 10)

        return generatorToken(
            header = getHeader(),
            claims = getClaims(userId = userId, tokenType = TokenType.ACCESS_TOKEN),
            expireTime = expireTime)
    }

    override fun createRefreshToken(userId: Long): String {
        val expireTime = Date()
        expireTime.setTime(expireTime.time + 1000 * 60 * 60 * 24 * 14)

        return generatorToken(
            header = getHeader(),
            claims = getClaims(userId = userId, tokenType = TokenType.REFRESH_TOKEN),
            expireTime = expireTime)
    }

    override fun validateAccessToken(token: String): Payload {
        try {
            return validateToken(token, TokenType.ACCESS_TOKEN)
        } catch (e: ExpiredJwtException) {
            throw UnAuthorizedException("토큰이 만료되었습니다.")
        } catch (e: JwtException) {
            throw JwtException("토큰이 유효하지 않습니다.")
        }
    }

    override fun validateRefreshToken(token: String): Payload {
        try {
            return validateToken(token, TokenType.REFRESH_TOKEN)
        } catch (e: ExpiredJwtException) {
            throw UnAuthorizedException("리프레시 토큰이 만료되었습니다.")
        } catch (e: JwtException) {
            throw JwtException("토큰이 유효하지 않습니다.")
        }
    }

    private fun validateToken(token: String, tokenType: TokenType): Payload {
        val claims = parseClaims(token)

        validateTokenType(claims = claims, tokenType = tokenType)

        return Payload(
            claims.get("userId", Long::class.java),
            TokenType.ACCESS_TOKEN
        )
    }

    private fun getHeader(): MutableMap<String, Any> {
        val headerMap: MutableMap<String, Any> = HashMap()
        headerMap["typ"] = "JWT"
        headerMap["alg"] = algorithm

        return headerMap
    }

    private fun getClaims(userId: Long, tokenType: TokenType): MutableMap<String, Any>{
        val claims: MutableMap<String, Any> = HashMap()
        claims["userId"] = userId
        claims["tokenType"] = tokenType.getValue()

        return claims
    }

    private fun validateTokenType(claims: Claims, tokenType: TokenType): Boolean {
        if (claims.get<Int>("tokenType", Int::class.java) != tokenType.getValue()) {
            throw UnAuthorizedException("올바른 토큰타입이 아닙니다.")
        }

        return true
    }

    private fun parseClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(secretKeyHash())
            .build()
            .parseClaimsJws(token)
            .body
    }

    private fun secretKeyHash(): Key {
        return Keys.hmacShaKeyFor(secretKey.toByteArray())
    }

    private fun generatorToken(
        header: MutableMap<String, Any>,
        claims: MutableMap<String, Any>,
        expireTime: Date): String {

        return Jwts.builder()
            .setHeader(header)
            .setClaims(claims)
            .setExpiration(expireTime)
            .signWith(secretKeyHash(), io.jsonwebtoken.SignatureAlgorithm.HS256)
            .compact();
    }
}