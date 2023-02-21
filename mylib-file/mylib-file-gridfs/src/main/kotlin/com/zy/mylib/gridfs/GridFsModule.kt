package com.zy.mylib.gridfs

import com.mongodb.ConnectionString
import com.mongodb.client.gridfs.GridFSBucket
import com.mongodb.client.gridfs.GridFSBuckets
import org.springframework.boot.autoconfigure.mongo.MongoProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory
import org.springframework.data.mongodb.gridfs.GridFsTemplate
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import javax.inject.Named

@Configuration
@ComponentScan("com.zy.mylib.gridfs")
@EnableMongoRepositories(basePackages = ["com.zy.mylib.gridfs.dao"], mongoTemplateRef = "gridFsMongoTemplate",
    createIndexesForQueryMethods = true
)
@EnableConfigurationProperties
open class GridFsModule {

  @Bean("gridFsProperties")
  @ConfigurationProperties(prefix = "spring.data.mongodb")
  open fun gridFsProperties(): MongoProperties {
    return MongoProperties()
  }

//  @Bean("gridFsMongoClient")
//  open fun mongoClient(@Named("gridFsProperties") mongoProperties: MongoProperties): MongoClient {
//    val mongo = MongoClientFactoryBean()
//    val conn = ConnectionString(mongoProperties.uri)
//    mongo.setConnectionString(conn)
//    if (mongoProperties.port != null) mongo.setPort(mongoProperties.port)
//    val username = conn.username.ifBlank { mongoProperties.username }
//    val host = if (conn.hosts != null && conn.hosts.isNotEmpty()) conn.hosts.joinToString { "," } else mongoProperties.host
//    val database = if (mongoProperties.database != null) mongoProperties.database else conn.database
//    val password = if (conn.password != null && conn.password.isNotEmpty()) conn.password else mongoProperties.password
//    val credential = MongoCredential.createCredential(username, database, password)
//    mongo.setCredential(arrayOf(credential))
//    mongo.setHost(host)
//
//    return mongo.getObject()
//  }

  @Bean("gridFsMongoDBFactory")
  open fun mongoDatabaseFactory(
      @Named("gridFsProperties") mongoProperties: MongoProperties): MongoDatabaseFactory {
    val conn = ConnectionString(mongoProperties.uri)
//    val database = if (mongoProperties.database != null) mongoProperties.database else conn.database
    return SimpleMongoClientDatabaseFactory(conn)
  }

  @Bean("gridFsMongoTemplate")
  open fun mongoTemplate(@Named("gridFsMongoDBFactory") mongoDatabaseFactory: MongoDatabaseFactory): MongoTemplate? {
    return MongoTemplate(mongoDatabaseFactory)
  }

  @Bean("gridFsTemplate")
  open fun gridFsTemplate(@Named("gridFsMongoTemplate") mongoTemplate: MongoTemplate): GridFsTemplate {
    return GridFsTemplate(mongoTemplate.mongoDatabaseFactory, mongoTemplate.converter)
  }

  @Bean("gridFsGridFSBucket")
  open fun getGridFSBucket(@Named("gridFsMongoTemplate") mongoTemplate: MongoTemplate): GridFSBucket? {
    return GridFSBuckets.create(mongoTemplate.db)
  }

}