package com.xrosstools.xbehavior.sample;
import com.xrosstools.xbehavior.Behavior;
import com.xrosstools.xbehavior.Blackboard;
import com.xrosstools.xbehavior.StatusEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BehaviorUnitTest {
    Blackboard bb;
    @Before
    public void setUp() {
        bb = new Blackboard();
    }

    @Test
    public void testSequence() {
        Assert.assertEquals(StatusEnum.FAILURE, Model_unit_test.SequenceFailure.create().tick(bb));
        Assert.assertEquals(StatusEnum.SUCCESS, Model_unit_test.SequenceSuccess.create().tick(bb));
        Assert.assertEquals(StatusEnum.RUNNING, Model_unit_test.SequenceRunning.create().tick(bb));
    }

    @Test
    public void testSelector() {
        Assert.assertEquals(StatusEnum.FAILURE, Model_unit_test.SelectorFailure.create().tick(bb));
        Assert.assertEquals(StatusEnum.SUCCESS, Model_unit_test.SelectorSuccess.create().tick(bb));
        Assert.assertEquals(StatusEnum.RUNNING, Model_unit_test.SelectorRunning.create().tick(bb));
    }

    @Test
    public void testParallel() {
        Assert.assertEquals(StatusEnum.FAILURE, Model_unit_test.ParallelFailure.create().tick(bb));
        Assert.assertEquals(StatusEnum.SUCCESS, Model_unit_test.ParallelSuccess.create().tick(bb));
        Assert.assertEquals(StatusEnum.RUNNING, Model_unit_test.ParallelRunning.create().tick(bb));
    }

    @Test
    public void testInverter() {
        Assert.assertEquals(StatusEnum.FAILURE, Model_unit_test.InverterFailure.create().tick(bb));
        Assert.assertEquals(StatusEnum.SUCCESS, Model_unit_test.InverterSuccess.create().tick(bb));
        Assert.assertEquals(StatusEnum.RUNNING, Model_unit_test.InverterRunning.create().tick(bb));
    }

    @Test
    public void testRepeatCount() {
        Assert.assertEquals(StatusEnum.FAILURE, Model_unit_test.RepeatFailure.create().tick(bb));
        Assert.assertEquals(StatusEnum.SUCCESS, Model_unit_test.RepeatSuccess.create().tick(bb));
        Assert.assertEquals(StatusEnum.RUNNING, Model_unit_test.RepeatRunning.create().tick(bb));
    }

    @Test
    public void testRepeatTimeoutSuccess() {
        StatusEnum statusEnum;
        Behavior behavior = Model_unit_test.RepeatSuccessTimeout.create();
        long start = System.currentTimeMillis();
        while ((statusEnum = behavior.tick(bb)) == StatusEnum.RUNNING) ;
        start = System.currentTimeMillis() - start;
        System.out.println(start);
        assertTrue(System.currentTimeMillis() - start >= 1000);
        assertEquals(StatusEnum.SUCCESS, statusEnum);
        //When timeout, if the last tick is running,. it will still end with success
    }

    @Test
    public void testRepeatTimeoutFailure() {
        StatusEnum statusEnum;
        Behavior behavior = Model_unit_test.RepeatFailureTimeout.create();
        long start = System.currentTimeMillis();
        while((statusEnum = behavior.tick(bb)) == StatusEnum.RUNNING);
        start = System.currentTimeMillis() - start;
        System.out.println(start);
        assertTrue(System.currentTimeMillis() - start >= 1000);
        assertEquals(StatusEnum.FAILURE, statusEnum);
    }

    @Test
    public void testRepeatTimeoutRunning() {
        StatusEnum statusEnum;
        Behavior behavior = Model_unit_test.RepeatRunningTimeout.create();
        long start = System.currentTimeMillis();
        while((statusEnum = behavior.tick(bb)) == StatusEnum.RUNNING);
        start = System.currentTimeMillis() - start;
        System.out.println(start);
        assertTrue(System.currentTimeMillis() - start >= 1000);
        assertEquals(StatusEnum.SUCCESS, statusEnum);
        //When timeout, if the last tick is running,. it will still end with success
    }

    @Test
    public void testRetryCount() {
        Assert.assertEquals(StatusEnum.FAILURE, Model_unit_test.RetryFailure.create().tick(bb));
        Assert.assertEquals(StatusEnum.SUCCESS, Model_unit_test.RetrySuccess.create().tick(bb));
        Assert.assertEquals(StatusEnum.RUNNING, Model_unit_test.RetryRunning.create().tick(bb));
    }

    @Test
    public void testRetryTimeoutSuccess() {
        StatusEnum statusEnum;
        Behavior behavior = Model_unit_test.RetrySuccessTimeout.create();
        long start = System.currentTimeMillis();
        while ((statusEnum = behavior.tick(bb)) == StatusEnum.RUNNING) ;
        start = System.currentTimeMillis() - start;
        System.out.println(start);
        assertTrue(System.currentTimeMillis() - start >= 1000);
        assertEquals(StatusEnum.SUCCESS, statusEnum);
    }

    @Test
    public void testRetryTimeoutFailure() {
        StatusEnum statusEnum;
        Behavior behavior = Model_unit_test.RetryFailureTimeout.create();
        long start = System.currentTimeMillis();
        while((statusEnum = behavior.tick(bb)) == StatusEnum.RUNNING);
        start = System.currentTimeMillis() - start;
        System.out.println(start);
        assertTrue(System.currentTimeMillis() - start >= 1000);
        assertEquals(StatusEnum.FAILURE, statusEnum);
        //When timeout, if the last tick is running,. it will still end with success
    }

    @Test
    public void testRetryTimeoutRunning() {
        StatusEnum statusEnum;
        Behavior behavior = Model_unit_test.RetryRunningTimeout.create();
        long start = System.currentTimeMillis();
        while((statusEnum = behavior.tick(bb)) == StatusEnum.RUNNING);
        start = System.currentTimeMillis() - start;
        System.out.println(start);
        assertTrue(System.currentTimeMillis() - start >= 1000);
        assertEquals(StatusEnum.FAILURE, statusEnum);
        //When timeout, if the last tick is running,. it will still end with failure
    }

    @Test
    public void testWaitSuccess() {
        StatusEnum statusEnum;
        Behavior behavior = Model_unit_test.WaitSuccess.create();
        long start = System.currentTimeMillis();
        while((statusEnum = behavior.tick(bb)) == StatusEnum.RUNNING);
        start = System.currentTimeMillis() - start;
        System.out.println(start);
        assertTrue(System.currentTimeMillis() - start >= 1000);
        assertEquals(StatusEnum.SUCCESS, statusEnum);
    }

    @Test
    public void testWaitFailure() {
        StatusEnum statusEnum;
        Behavior behavior = Model_unit_test.WaitFailure.create();
        long start = System.currentTimeMillis();
        while((statusEnum = behavior.tick(bb)) == StatusEnum.RUNNING);
        start = System.currentTimeMillis() - start;
        System.out.println(start);
        assertTrue(System.currentTimeMillis() - start >= 1000);
        assertEquals(StatusEnum.FAILURE, statusEnum);
    }

//    @Test
//    public void testWaitRunning() {
//        StatusEnum statusEnum;
//        Behavior behavior = Model_unit_test.WaitRunning.create();
//        long start = System.currentTimeMillis();
//        while((statusEnum = behavior.tick(bb)) == StatusEnum.RUNNING);
//        start = System.currentTimeMillis() - start;
//        System.out.println(start);
//        assertTrue(System.currentTimeMillis() - start >= 1000);
//        assertEquals(StatusEnum.SUCCESS, statusEnum);
//    }

    @Test
    public void testForceSuccess() {
        Assert.assertEquals(StatusEnum.SUCCESS, Model_unit_test.ForceSucess.create().tick(bb));
        Assert.assertEquals(StatusEnum.RUNNING, Model_unit_test.ForceSuccessRunning.create().tick(bb));
    }

    @Test
    public void testForceFailure() {
        Assert.assertEquals(StatusEnum.FAILURE, Model_unit_test.ForceFailure.create().tick(bb));
        Assert.assertEquals(StatusEnum.RUNNING, Model_unit_test.ForceFailureRunning.create().tick(bb));
    }

    @Test
    public void testSleep() {
        StatusEnum statusEnum;
        Behavior behavior = Model_unit_test.Sleep.create();
        long start = System.currentTimeMillis();
        while((statusEnum = behavior.tick(bb)) == StatusEnum.RUNNING);
        start = System.currentTimeMillis() - start;
        System.out.println(start);
        assertTrue(System.currentTimeMillis() - start >= 1000);
        assertEquals(StatusEnum.SUCCESS, statusEnum);
    }

    @Test
    public void testFailure() {
        Behavior behavior = Model_unit_test.Failure.create();
        assertEquals(StatusEnum.FAILURE, behavior.tick(bb));
        assertEquals(StatusEnum.FAILURE, behavior.tick(bb));
        assertEquals(StatusEnum.FAILURE, behavior.tick(bb));
    }

    @Test
    public void testSuccess() {
        Behavior behavior = Model_unit_test.Success.create();
        assertEquals(StatusEnum.SUCCESS, behavior.tick(bb));
        assertEquals(StatusEnum.SUCCESS, behavior.tick(bb));
        assertEquals(StatusEnum.SUCCESS, behavior.tick(bb));
    }

    @Test
    public void testSubtree() {
        Behavior behavior = Model_unit_test.SubtreeSucess.create();
        assertEquals(StatusEnum.SUCCESS, behavior.tick(bb));
        assertEquals(StatusEnum.SUCCESS, behavior.tick(bb));
        assertEquals(StatusEnum.SUCCESS, behavior.tick(bb));
    }

    @Test
    public void testCallbackFalse() {
        Behavior behavior = Model_unit_test.CallbackFalse.create();
        bb.set(ConditionByA.A, false);
        assertEquals(StatusEnum.FAILURE, behavior.tick(bb));
        assertEquals(StatusEnum.FAILURE, behavior.tick(bb));
        assertEquals(StatusEnum.FAILURE, behavior.tick(bb));
    }

    @Test
    public void testCallbackTrue() {
        Behavior behavior = Model_unit_test.CallbackTrue.create();
        bb.set(ConditionByA.A, true);
        assertEquals(StatusEnum.SUCCESS, behavior.tick(bb));
        assertEquals(StatusEnum.SUCCESS, behavior.tick(bb));
        assertEquals(StatusEnum.SUCCESS, behavior.tick(bb));
    }

    @Test
    public void testExpressionFalse() {
        Behavior behavior = Model_unit_test.ExpressionFalse.create();
        bb.set(ConditionByA.A, 123);
        bb.set(ConditionByA.B, 456);
        assertEquals(StatusEnum.FAILURE, behavior.tick(bb));

        bb.set(ConditionByA.A, 0.1);
        bb.set(ConditionByA.B, 0.2);
        assertEquals(StatusEnum.FAILURE, behavior.tick(bb));

        bb.set(ConditionByA.A, "ABC");
        bb.set(ConditionByA.B, "DEF");
        assertEquals(StatusEnum.FAILURE, behavior.tick(bb));
    }

    @Test
    public void testExpressionTrue() {
        Behavior behavior = Model_unit_test.ExpressionTrue.create();
        bb.set(ConditionByA.A, 1233);
        assertEquals(StatusEnum.SUCCESS, behavior.tick(bb));

        bb.set(ConditionByA.A, 12.34);
        assertEquals(StatusEnum.SUCCESS, behavior.tick(bb));

        bb.set(ConditionByA.A, 11L);
        assertEquals(StatusEnum.SUCCESS, behavior.tick(bb));
    }
}

