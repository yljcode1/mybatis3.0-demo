package org.apache.ibatis.test;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * @author xiaoK
 * @date 2022/7/14
 */

@Slf4j
public class MyTest {
    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 加载mybatis-config.xml配置文件，并且创建SqlSessionFactory对象
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(inputStream);
        // 创建sqlSession对象
        SqlSession sqlSession = build.openSession();
        try {
            HashMap<String, Object> parameter = new HashMap<>();
            parameter.put("id", 1);
            //执行select语句，将ResultSet映射成对象并且返回
            Blog blog = (Blog) sqlSession.selectOne("com.xxx.BlogMapper.selectBlogDetails", parameter);
            //输出对象
            log.info("{}", blog.toString());
        } catch (Exception e) {
            log.info("{}",e);
        }
    }
}

@Data
class Blog {
    private int id;
    private String title;
    private Author author;
    private List<Post> posts;
}

@Data
class Author {
    private int id;
    private String username;
    private String password;
    private String email;
}

@Data
class Post {
    private int id;
    protected Author author;
    protected String content;
}