package com.example.cocos2dtest;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.ease.CCEaseIn;
import org.cocos2d.actions.ease.CCEaseOut;
import org.cocos2d.actions.instant.CCHide;
import org.cocos2d.actions.instant.CCShow;
import org.cocos2d.actions.interval.CCBezierBy;
import org.cocos2d.actions.interval.CCBlink;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCIntervalAction;
import org.cocos2d.actions.interval.CCJumpBy;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCRotateBy;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.actions.interval.CCTintBy;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CCBezierConfig;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor3B;

public class ActionLayer extends CCLayer {

	public ActionLayer() {
		init();
	}

	private void init() {
		// CCAction
		// 受时间限制
		// CCFiniteTimeAction
		// 立即执行的。。。
		// CCInstantAction;

		// 延时执行的。。。
		// CCIntervalAction
		// 不受时间限制

		// CCFollow
		// CCRepeatForever // 行走的序列播放 知道英雄消失

		// CCSpeed // 游戏速度的控制

		// move();
		// move1();
		// jump();
		// 抛物线
		// bezier();
		// 渐变
		// ease();
		// 缩放
		// scale();
		// 颜色渐变
		// tint();
		// 闪烁
		// blink();
		// 在跳跃的过程中增加并发的动作（旋转）
		jump1();
	}

	/**
	 * 在跳跃的过程中增加并发的动作（旋转）
	 */
	private void jump1() {
		CGPoint pos = CGPoint.ccp(350, 200);
		CCJumpBy jumpBy = CCJumpBy.action(2, pos , 150, 2);
		
		CCRotateBy rotateBy = CCRotateBy.action(1, 360);
		
		// 并发执行的动作
		CCSpawn spawn = CCSpawn.actions(jumpBy, rotateBy);
		CCIntervalAction reverse = spawn.reverse();
		
		// 串联的动作
		CCSequence sequence = CCSequence.actions(spawn, reverse,CCDelayTime.action(1));
		
		CCRepeatForever forever = CCRepeatForever.action(sequence);
		CCSprite sprite = getSprite();
		sprite.setAnchorPoint(.5f, .5f);
		sprite.setPosition(80, 50);
		sprite.runAction(forever);
	}

	/**
	 * 闪烁
	 */
	private void blink() {
		CCBlink blink = CCBlink.action(2, 3);
		// getSprite().runAction(blink);
		CCHide hide = CCHide.action();// 隐藏
		CCShow show = CCShow.action();// 显示

		CCSequence sequence = CCSequence.actions(blink, hide,
				CCDelayTime.action(1), show);

		CCSprite sprite = getSprite();
		sprite.setPosition(100, 100);
		sprite.runAction(sequence);
	}

	private void tint() {
		ccColor3B c = ccColor3B.ccc3(0, -100, 0);
		CCTintBy tintBy = CCTintBy.action(2, c);// RGB颜色值的变动
		// 显示文字内容，字体，大小
		CCLabel label = CCLabel.makeLabel("你好啊~~我喜欢你", "0", 20);
		label.setColor(ccColor3B.ccc3(255, 288, 0));
		label.setAnchorPoint(0, 0);
		label.setPosition(150, 100);
		addChild(label);

		label.runAction(CCRepeatForever.action(CCSequence.actions(tintBy,
				tintBy.reverse())));
	}

	private CCSprite getHeart() {
		CCSprite heart = CCSprite.sprite("z_1_attack_01.png");
		addChild(heart);
		return heart;
	}

	private void scale() {
		// 心跳
		CCScaleBy scaleBy = CCScaleBy.action(.5f, 1.5f);
		CCSprite heart = getHeart();
		heart.setAnchorPoint(0, 0);
		CCSequence sequence = CCSequence.actions(scaleBy, scaleBy.reverse());
		CCRepeatForever forever = CCRepeatForever.action(sequence);
		heart.runAction(forever);
	}

	private void ease() {
		CGPoint pos = CGPoint.ccp(250, 200);
		CCMoveBy moveBy = CCMoveBy.action(2, pos);
		// 渐快，加速度值恒定
		CCEaseIn easeIn = CCEaseIn.action(moveBy, 10);
		// 渐慢
		CCEaseOut easeOut = CCEaseOut.action(moveBy.reverse(), 10);
		getSprite().runAction(
				CCRepeatForever.action(CCSequence.actions(easeIn, easeOut)));
	}

	/**
	 * 抛物线
	 */
	private void bezier() {
		CCBezierConfig c = new CCBezierConfig();
		// 重点
		c.controlPoint_1 = CGPoint.ccp(0, 0);
		// 最高点
		c.controlPoint_2 = CGPoint.ccp(200, 100);
		c.endPosition = CGPoint.ccp(300, 0);
		CCBezierBy bezierBy = CCBezierBy.action(2, c);
		// 设置来回循环的运动
		// CCSequence sequence = CCSequence.actions(bezierBy,
		// bezierBy.reverse());
		// CCRepeatForever forever = CCRepeatForever.action(sequence);

		CCSprite sprite = getSprite();
		sprite.runAction(bezierBy);
	}

	/**
	 * 跳跃
	 */
	private void jump() {
		// 跳跃：启动，时间，位置，高度(最高点的位置)，次数
		CGPoint pos = CGPoint.ccp(300, 150);
		CCJumpBy jumpBy = CCJumpBy.action(2, pos, 100, 2);

		CCSprite sprite = getSprite();
		sprite.runAction(jumpBy);
	}

	/**
	 * 移动
	 */
	private void move1() {
		CGPoint pos = CGPoint.ccp(100, 150);
		// 移动的起点，目标点，时间间隔
		CCMoveBy moveBy = CCMoveBy.action(2, pos);
		CCSprite sprite = getSprite();
		sprite.setPosition(100, 0);
		// moveBy.reverse();
		// 按照顺序执行，将动作串联起来
		CCSequence sequence = CCSequence.actions(moveBy, moveBy.reverse());
		sprite.runAction(sequence);
	}

	private void move() {
		CGPoint pos = CGPoint.ccp(100, 150);
		// 移动的起点，目标点，时间间隔
		CCMoveTo moveTo = CCMoveTo.action(2, pos);
		CCSprite sprite = getSprite();
		sprite.runAction(moveTo);
	}

	private CCSprite getSprite() {
		CCSprite sprite = CCSprite.sprite("z_1_attack_01.png");
		sprite.setAnchorPoint(0, 0);// 设置锚点
		this.addChild(sprite);
		return sprite;
	}
}
