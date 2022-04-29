/*
 * Copyright © 2020 ismezy (ismezy@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zy.mylib.utils

import org.springframework.util.Base64Utils
import java.io.ByteArrayOutputStream
import java.security.Key
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.Signature
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

/**
 *
 *
 * RSA公钥/私钥/签名工具包
 *
 *
 * 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式<br></br>
 * 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，<br></br>
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全
 *
 */
object RSAUtils {
  /**
   * 加密算法RSA
   */
  const val KEY_ALGORITHM = "RSA"

  /**
   * 签名算法
   */
  const val SIGNATURE_ALGORITHM = "MD5withRSA"

  /**
   * 获取公钥的key
   */
  private const val PUBLIC_KEY = "LocatorPublicKey"

  /**
   * 获取私钥的key
   */
  private const val PRIVATE_KEY = "LocatorPrivateKey"

  /**
   * RSA最大加密明文大小
   */
  private const val MAX_ENCRYPT_BLOCK = 117

  /**
   * RSA最大解密密文大小
   */
  private const val MAX_DECRYPT_BLOCK = 128

  /**
   *
   *
   * 生成密钥对(公钥和私钥)
   *
   *
   * @return
   * @throws Exception
   */
  @Throws(Exception::class)
  fun genKeyPair(): Map<String, Any> {
    val keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM)
    keyPairGen.initialize(1024)
    val keyPair = keyPairGen.generateKeyPair()
    val publicKey = keyPair.public as RSAPublicKey
    val privateKey = keyPair.private as RSAPrivateKey
    val keyMap: MutableMap<String, Any> = HashMap(2)
    keyMap[PUBLIC_KEY] = publicKey
    keyMap[PRIVATE_KEY] = privateKey
    return keyMap
  }

  /**
   *
   *
   * 用私钥对信息生成数字签名
   *
   *
   * @param data       已加密数据
   * @param privateKey 私钥(BASE64编码)
   * @return
   * @throws Exception
   */
  @Throws(Exception::class)
  fun sign(data: ByteArray?, privateKey: String?): String {
    val keyBytes = Base64Utils.decodeFromString(privateKey)
    val pkcs8KeySpec = PKCS8EncodedKeySpec(keyBytes)
    val keyFactory = KeyFactory.getInstance(KEY_ALGORITHM)
    val privateK = keyFactory.generatePrivate(pkcs8KeySpec)
    val signature = Signature.getInstance(SIGNATURE_ALGORITHM)
    signature.initSign(privateK)
    signature.update(data)
    return Base64Utils.encodeToString(signature.sign())
  }

  /**
   *
   *
   * 校验数字签名
   *
   *
   * @param data      已加密数据
   * @param publicKey 公钥(BASE64编码)
   * @param sign      数字签名
   * @return
   * @throws Exception
   */
  @Throws(Exception::class)
  fun verify(data: ByteArray?, publicKey: String?, sign: String?): Boolean {
    val keyBytes = Base64Utils.decodeFromString(publicKey)
    val keySpec = X509EncodedKeySpec(keyBytes)
    val keyFactory = KeyFactory.getInstance(KEY_ALGORITHM)
    val publicK = keyFactory.generatePublic(keySpec)
    val signature = Signature.getInstance(SIGNATURE_ALGORITHM)
    signature.initVerify(publicK)
    signature.update(data)
    return signature.verify(Base64Utils.decodeFromString(sign))
  }

  /**
   * <P>
   * 私钥解密
  </P> *
   *
   * @param encryptedData 已加密数据
   * @param privateKey    私钥(BASE64编码)
   * @return
   * @throws Exception
   */
  @Throws(Exception::class)
  fun decryptByPrivateKey(encryptedData: ByteArray, privateKey: String?): ByteArray {
    val keyBytes = Base64Utils.decodeFromString(privateKey)
    val pkcs8KeySpec = PKCS8EncodedKeySpec(keyBytes)
    val keyFactory = KeyFactory.getInstance(KEY_ALGORITHM)
    val privateK: Key = keyFactory.generatePrivate(pkcs8KeySpec)
    val cipher = Cipher.getInstance(keyFactory.algorithm)
    cipher.init(Cipher.DECRYPT_MODE, privateK)
    val inputLen = encryptedData.size
    val out = ByteArrayOutputStream()
    var offSet = 0
    var cache: ByteArray
    var i = 0
    // 对数据分段解密
    while (inputLen - offSet > 0) {
      cache = if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
        cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK)
      } else {
        cipher.doFinal(encryptedData, offSet, inputLen - offSet)
      }
      out.write(cache, 0, cache.size)
      i++
      offSet = i * MAX_DECRYPT_BLOCK
    }
    val decryptedData = out.toByteArray()
    out.close()
    return decryptedData
  }

  /**
   *
   *
   * 公钥解密
   *
   *
   * @param encryptedData 已加密数据
   * @param publicKey     公钥(BASE64编码)
   * @return
   * @throws Exception
   */
  @Throws(Exception::class)
  fun decryptByPublicKey(encryptedData: ByteArray, publicKey: String?): ByteArray {
    val keyBytes = Base64Utils.decodeFromString(publicKey)
    val x509KeySpec = X509EncodedKeySpec(keyBytes)
    val keyFactory = KeyFactory.getInstance(KEY_ALGORITHM)
    val publicK: Key = keyFactory.generatePublic(x509KeySpec)
    val cipher = Cipher.getInstance(keyFactory.algorithm)
    cipher.init(Cipher.DECRYPT_MODE, publicK)
    val inputLen = encryptedData.size
    val out = ByteArrayOutputStream()
    var offSet = 0
    var cache: ByteArray
    var i = 0
    // 对数据分段解密
    while (inputLen - offSet > 0) {
      cache = if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
        cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK)
      } else {
        cipher.doFinal(encryptedData, offSet, inputLen - offSet)
      }
      out.write(cache, 0, cache.size)
      i++
      offSet = i * MAX_DECRYPT_BLOCK
    }
    val decryptedData = out.toByteArray()
    out.close()
    return decryptedData
  }

  /**
   *
   *
   * 公钥加密
   *
   *
   * @param data      源数据
   * @param publicKey 公钥(BASE64编码)
   * @return
   * @throws Exception
   */
  @Throws(Exception::class)
  fun encryptByPublicKey(data: ByteArray, publicKey: String?): ByteArray {
    val keyBytes = Base64Utils.decodeFromString(publicKey)
    val x509KeySpec = X509EncodedKeySpec(keyBytes)
    val keyFactory = KeyFactory.getInstance(KEY_ALGORITHM)
    val publicK: Key = keyFactory.generatePublic(x509KeySpec)
    // 对数据加密
    val cipher = Cipher.getInstance(keyFactory.algorithm)
    cipher.init(Cipher.ENCRYPT_MODE, publicK)
    val inputLen = data.size
    val out = ByteArrayOutputStream()
    var offSet = 0
    var cache: ByteArray
    var i = 0
    // 对数据分段加密
    while (inputLen - offSet > 0) {
      cache = if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
        cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK)
      } else {
        cipher.doFinal(data, offSet, inputLen - offSet)
      }
      out.write(cache, 0, cache.size)
      i++
      offSet = i * MAX_ENCRYPT_BLOCK
    }
    val encryptedData = out.toByteArray()
    out.close()
    return encryptedData
  }

  /**
   *
   *
   * 私钥加密
   *
   *
   * @param data       源数据
   * @param privateKey 私钥(BASE64编码)
   * @return
   * @throws Exception
   */
  @Throws(Exception::class)
  fun encryptByPrivateKey(data: ByteArray, privateKey: String?): ByteArray {
    val keyBytes = Base64Utils.decodeFromString(privateKey)
    val pkcs8KeySpec = PKCS8EncodedKeySpec(keyBytes)
    val keyFactory = KeyFactory.getInstance(KEY_ALGORITHM)
    val privateK: Key = keyFactory.generatePrivate(pkcs8KeySpec)
    val cipher = Cipher.getInstance(keyFactory.algorithm)
    cipher.init(Cipher.ENCRYPT_MODE, privateK)
    val inputLen = data.size
    val out = ByteArrayOutputStream()
    var offSet = 0
    var cache: ByteArray
    var i = 0
    // 对数据分段加密
    while (inputLen - offSet > 0) {
      cache = if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
        cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK)
      } else {
        cipher.doFinal(data, offSet, inputLen - offSet)
      }
      out.write(cache, 0, cache.size)
      i++
      offSet = i * MAX_ENCRYPT_BLOCK
    }
    val encryptedData = out.toByteArray()
    out.close()
    return encryptedData
  }

  /**
   *
   *
   * 获取私钥
   *
   *
   * @param keyMap 密钥对
   * @return
   * @throws Exception
   */
  @Throws(Exception::class)
  fun getPrivateKey(keyMap: Map<String?, Any?>): String {
    val key = keyMap[PRIVATE_KEY] as Key?
    return Base64Utils.encodeToString(key!!.encoded)
  }

  /**
   *
   *
   * 获取公钥
   *
   *
   * @param keyMap 密钥对
   * @return
   * @throws Exception
   */
  @Throws(Exception::class)
  fun getPublicKey(keyMap: Map<String?, Any?>): String {
    val key = keyMap[PUBLIC_KEY] as Key?
    return Base64Utils.encodeToString(key!!.encoded)
  }
}
