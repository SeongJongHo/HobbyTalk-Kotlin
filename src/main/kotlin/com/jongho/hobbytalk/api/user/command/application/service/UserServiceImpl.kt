package com.jongho.hobbytalk.api.user.command.application.service

import com.jongho.hobbytalk.api.user.command.application.repository.UserRepository
import com.jongho.hobbytalk.api.user.command.common.exception.UserDuplicatedException
import com.jongho.hobbytalk.api.user.command.domain.model.User
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(private val userRepository: UserRepository): UserService{
    @Transactional
    override fun signUp(user: User): Long {
        if(userRepository.findOneByUsername(user.username) !== null) {
            throw UserDuplicatedException("이미 존재하는 아이디입니다.")
        }
        if(userRepository.findOneByPhoneNumber(user.phoneNumber) !== null) {
            throw UserDuplicatedException("이미 가입된 전화번호입니다.")
        }

        val userId: Long
        try {
            userId = userRepository.save(user)
        }
        catch (e: DataIntegrityViolationException) {
            throw UserDuplicatedException("이미 가입된 아이디거나 전화번호입니다.")
        }
        catch (e: Exception) {
            throw RuntimeException("회원가입에 실패하였습니다.")
        }

        return userId
    }
}