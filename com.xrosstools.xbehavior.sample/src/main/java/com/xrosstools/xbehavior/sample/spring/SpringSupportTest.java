package com.xrosstools.xbehavior.sample.spring;

import com.xrosstools.xbehavior.Blackboard;
import com.xrosstools.xbehavior.StatusEnum;
import com.xrosstools.xbehavior.XbehaviorSpring;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.junit.Assert.assertEquals;

@Configuration
@ComponentScan
public class SpringSupportTest {
    @Bean
    XbehaviorSpring createSpringBeanFactory() {
        return new XbehaviorSpring();
    }

    @BeforeClass
    public static void setup() throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringSupportTest.class);
    }

    @Test
    public void testSuccess() {
        Blackboard bb = new Blackboard();
        bb.set("A", true);
        assertEquals(StatusEnum.SUCCESS, Spring_support.Sequence.create().tick(bb));
    }

    @Test
    public void testFailure() {
        Blackboard bb = new Blackboard();
        bb.set("A", false);
        assertEquals(StatusEnum.FAILURE, Spring_support.Sequence.create().tick(bb));
    }
}