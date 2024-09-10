package com.liferay.portal.cluster.multiple.test.rule;

import com.liferay.portal.kernel.test.rule.ClassTestRule;
import org.junit.runner.Description;

public class ClusteringTestRule extends ClassTestRule<Void> {

	public static final ClusteringTestRule INSTANCE =
		new ClusteringTestRule();

	@Override
	protected void afterClass(Description description, Void unused)
		throws Throwable {
		System.out.println("!!!AFTER CLASS!!!");
	}

	@Override
	protected Void beforeClass(Description description){
		System.out.println("!!!BEFORE CLASS!!!");
		return null;
	}

}
