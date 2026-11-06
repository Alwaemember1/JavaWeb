package com.henau.book.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Base64;

public final class PasswordUtil {

    private static final String ALGORITHM =
            "PBKDF2WithHmacSHA256";

    private static final int ITERATIONS = 600_000;
    private static final int KEY_LENGTH = 256;
    private static final int SALT_LENGTH = 16;

    private static final SecureRandom RANDOM = new SecureRandom();

    private PasswordUtil() {
    }

    public static String hash(String password) {
        // 1. 为这次密码处理生成随机盐
        byte[] salt = new byte[SALT_LENGTH];
        RANDOM.nextBytes(salt);

        // 2. 准备密码、盐、迭代次数和结果长度
        PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                salt,
                ITERATIONS,
                KEY_LENGTH
        );

        try {
            // 3. 调用 JDK 算法计算结果
            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance(ALGORITHM);

            byte[] hash = factory.generateSecret(spec).getEncoded();

            // 4. 将二进制数据编码成便于保存的字符串
            String saltText =
                    Base64.getEncoder().encodeToString(salt);

            String hashText =
                    Base64.getEncoder().encodeToString(hash);

            // 保存算法、参数、盐和哈希，供以后登录验证
            return ALGORITHM + "$"
                    + ITERATIONS + "$"
                    + saltText + "$"
                    + hashText;

        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("密码处理失败", e);
        } finally {
            spec.clearPassword();
        }
    }
}