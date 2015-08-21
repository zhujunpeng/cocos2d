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
		// ��ʱ������
		// CCFiniteTimeAction
		// ����ִ�еġ�����
		// CCInstantAction;

		// ��ʱִ�еġ�����
		// CCIntervalAction
		// ����ʱ������

		// CCFollow
		// CCRepeatForever // ���ߵ����в��� ֪��Ӣ����ʧ

		// CCSpeed // ��Ϸ�ٶȵĿ���

		// move();
		// move1();
		// jump();
		// ������
		// bezier();
		// ����
		// ease();
		// ����
		// scale();
		// ��ɫ����
		// tint();
		// ��˸
		// blink();
		// ����Ծ�Ĺ��������Ӳ����Ķ�������ת��
		jump1();
	}

	/**
	 * ����Ծ�Ĺ��������Ӳ����Ķ�������ת��
	 */
	private void jump1() {
		CGPoint pos = CGPoint.ccp(350, 200);
		CCJumpBy jumpBy = CCJumpBy.action(2, pos , 150, 2);
		
		CCRotateBy rotateBy = CCRotateBy.action(1, 360);
		
		// ����ִ�еĶ���
		CCSpawn spawn = CCSpawn.actions(jumpBy, rotateBy);
		CCIntervalAction reverse = spawn.reverse();
		
		// �����Ķ���
		CCSequence sequence = CCSequence.actions(spawn, reverse,CCDelayTime.action(1));
		
		CCRepeatForever forever = CCRepeatForever.action(sequence);
		CCSprite sprite = getSprite();
		sprite.setAnchorPoint(.5f, .5f);
		sprite.setPosition(80, 50);
		sprite.runAction(forever);
	}

	/**
	 * ��˸
	 */
	private void blink() {
		CCBlink blink = CCBlink.action(2, 3);
		// getSprite().runAction(blink);
		CCHide hide = CCHide.action();// ����
		CCShow show = CCShow.action();// ��ʾ

		CCSequence sequence = CCSequence.actions(blink, hide,
				CCDelayTime.action(1), show);

		CCSprite sprite = getSprite();
		sprite.setPosition(100, 100);
		sprite.runAction(sequence);
	}

	private void tint() {
		ccColor3B c = ccColor3B.ccc3(0, -100, 0);
		CCTintBy tintBy = CCTintBy.action(2, c);// RGB��ɫֵ�ı䶯
		// ��ʾ�������ݣ����壬��С
		CCLabel label = CCLabel.makeLabel("��ð�~~��ϲ����", "0", 20);
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
		// ����
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
		// ���죬���ٶ�ֵ�㶨
		CCEaseIn easeIn = CCEaseIn.action(moveBy, 10);
		// ����
		CCEaseOut easeOut = CCEaseOut.action(moveBy.reverse(), 10);
		getSprite().runAction(
				CCRepeatForever.action(CCSequence.actions(easeIn, easeOut)));
	}

	/**
	 * ������
	 */
	private void bezier() {
		CCBezierConfig c = new CCBezierConfig();
		// �ص�
		c.controlPoint_1 = CGPoint.ccp(0, 0);
		// ��ߵ�
		c.controlPoint_2 = CGPoint.ccp(200, 100);
		c.endPosition = CGPoint.ccp(300, 0);
		CCBezierBy bezierBy = CCBezierBy.action(2, c);
		// ��������ѭ�����˶�
		// CCSequence sequence = CCSequence.actions(bezierBy,
		// bezierBy.reverse());
		// CCRepeatForever forever = CCRepeatForever.action(sequence);

		CCSprite sprite = getSprite();
		sprite.runAction(bezierBy);
	}

	/**
	 * ��Ծ
	 */
	private void jump() {
		// ��Ծ��������ʱ�䣬λ�ã��߶�(��ߵ��λ��)������
		CGPoint pos = CGPoint.ccp(300, 150);
		CCJumpBy jumpBy = CCJumpBy.action(2, pos, 100, 2);

		CCSprite sprite = getSprite();
		sprite.runAction(jumpBy);
	}

	/**
	 * �ƶ�
	 */
	private void move1() {
		CGPoint pos = CGPoint.ccp(100, 150);
		// �ƶ�����㣬Ŀ��㣬ʱ����
		CCMoveBy moveBy = CCMoveBy.action(2, pos);
		CCSprite sprite = getSprite();
		sprite.setPosition(100, 0);
		// moveBy.reverse();
		// ����˳��ִ�У���������������
		CCSequence sequence = CCSequence.actions(moveBy, moveBy.reverse());
		sprite.runAction(sequence);
	}

	private void move() {
		CGPoint pos = CGPoint.ccp(100, 150);
		// �ƶ�����㣬Ŀ��㣬ʱ����
		CCMoveTo moveTo = CCMoveTo.action(2, pos);
		CCSprite sprite = getSprite();
		sprite.runAction(moveTo);
	}

	private CCSprite getSprite() {
		CCSprite sprite = CCSprite.sprite("z_1_attack_01.png");
		sprite.setAnchorPoint(0, 0);// ����ê��
		this.addChild(sprite);
		return sprite;
	}
}
