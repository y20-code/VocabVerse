package com.yls.vocabverse.service.impl;

import com.yls.vocabverse.entity.User;
import com.yls.vocabverse.mapper.UserMapper;
import com.yls.vocabverse.service.UserService;
import com.yls.vocabverse.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 用户服务接口的实现类。
 * 封装了所有与用户相关的核心业务逻辑，实现业务层与数据访问层的解耦。
 *
 * @author 杨林森
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;



    /**
     * 执行用户注册的业务逻辑。
     * 关键步骤包括：
     * 1. 校验用户名的唯一性，防止重复注册。
     * 2. 对明文密码进行加密处理，保障数据安全。
     * 3. 将校验和处理过的数据持久化到数据库。
     *
     * @param user 包含待注册用户信息的实体对象。
     * @throws RuntimeException 如果用户名已存在，则抛出此业务异常。
     */
    public void register(User user) {
        // 1. 业务校验
        User byUsername = userMapper.findByUsername(user.getUsername());
        if (byUsername != null) {
            // 通过抛出异常来中断流程并报告业务错误，是标准的处理方式
            throw new RuntimeException("用户名 '" + user.getUsername() + "'已经被注册了");
        }

        // 2. 核心处理
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);

        // 3. 数据持久化
        userMapper.insert(user);

    }


    /**
     * 执行用户登录验证的业务逻辑。
     * 关键步骤包括：
     * 1. 验证用户是否存在。
     * 2. 安全地比对用户输入的密码与数据库中存储的哈希密码。
     * 3. 验证成功后，生成JWT作为后续请求的身份凭证。
     *
     * @param user 包含登录用户名和密码的实体对象。
     * @return 如果认证成功，返回生成的JWT字符串。
     * @throws RuntimeException 如果用户不存在或密码错误，则抛出此业务异常。
     */
    public String login(User user) {
        // 1. 业务校验
        User byUsername = userMapper.findByUsername(user.getUsername());
        if (byUsername == null) {
            throw new RuntimeException("用户不存在！");
        }

        // 2. 核心处理
        if (!passwordEncoder.matches(user.getPassword(), byUsername.getPassword())) {
            throw new RuntimeException("密码错误！");
        }

        // 3. 生成结果
        return jwtUtil.generateToken(byUsername.getId(),byUsername.getUsername(),byUsername.getRole());

    }
}
