package com.example.cocos2dtest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.cocos2d.actions.base.CCFollow;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.instant.CCPlace;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCTMXObjectGroup;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.particlesystem.CCParticleSnow;
import org.cocos2d.particlesystem.CCParticleSystem;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.util.CGPointUtil;

import android.R.integer;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.view.MotionEvent;

/**
 * ��Ҫ��layer
 * 
 */
public class DemoLayer extends CCLayer {

	private CCTMXTiledMap gameMap;
	private List<CGPoint> roads;
	// private List<CCMoveTo> moveList;
	private CCSprite sprite;

	public DemoLayer() {
		setIsTouchEnabled(true);
		init();
	}

	private void init() {
		// 1����һ������ʮ�����С·��
		// �ṩ����ͼ
		// a,ϸ�ڴ���ͼƬ��Դ���·��
		// b ���ƵĶ��壬����������
		// c �����洢���ݵ�˳�򣨴����·��
		loadMap();
		// �����ӹ��ĵ�ͼ-�����Ľ���洢��λ��
		loadRoad();
		// ��ȡ����·�Ĺ����
		// 2��һ����ʬð�Ŵ�ѩǰ��
		// ǰ��
		move();
		// ����֡�Ĳ���
		animation();
		// Ʈѩ������������ϵͳ
		particleSystem();
		// 3�����ѩͣ��
		// ����ϵͳ����

		// 4����ʬ���˵���������
		// ������������
		// 1�����֣�2����Ч
		// ����+��ͣ+ֹͣ��
		// MediaPlayer ;SoundPool
		// �����Ŀ���
		// �������ݣ��ƶ���ͼ����
		// ���ͼ��С��ͼ��һ��
		// CCFollow
		// ���ͼ���ƶ���Ӣ������Ļ�м䣬����ֺڱ�
		// this.runAction(CCFollow.action(sprite));
		// С��ͼ
		// ��Ϸ��ͣ�Ĳ���
	}

	@Override
	public boolean ccTouchesMoved(MotionEvent event) {
		gameMap.touchMove(event, gameMap);

		return super.ccTouchesMoved(event);
	}

	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		// ��ʾ��ͣ��layer
		onExit();
		// �����ͣ��layer��������ӵ�������CCSense����
		PauseLayer layer = new PauseLayer();
		getParent().addChild(layer);

		return super.ccTouchesBegan(event);
	}

	/**
	 * ����ϵͳ
	 */
	private void particleSystem() {
		system = CCParticleSnow.node();
		// ����ͼƬ��Դ
		system.setTexture(CCTextureCache.sharedTextureCache().addImage("f.png"));
		// �������ϵͳ��layer��
		this.addChild(system);
	}

	private void animation() {
		// һ�������ͼƬ
		// ����ʱ����
		ArrayList<CCSpriteFrame> frames = new ArrayList<CCSpriteFrame>();
		// String fileName = "z_1_0%d.png";//1-9
		String fileName = "z_1_%02d.png";// (1-99)
		for (int i = 1; i <= 7; i++) {
			CCSpriteFrame frame = CCSprite.sprite(String.format(fileName, i))
					.displayedFrame();
			frames.add(frame);
		}
		CCAnimation animation = CCAnimation.animation("", 0.2f, frames);
		CCAnimate animate = CCAnimate.action(animation);// ��ͣ�ز���
		CCRepeatForever forever = CCRepeatForever.action(animate);
		sprite.runAction(forever);
	}

	/**
	 * �ƶ�
	 */
	private void move() {
		sprite = getSprite();
		sprite.setPosition(roads.get(0));
		// sprite.runAction(moveTo);

		// for (int i = 0; i < roads.size(); i++) {
		// CCMoveTo moveTo = CCMoveTo.action(2, roads.get(1));
		// sprite.runAction(moveTo);
		// }
		// һ��
		// CCSequence sequence = CCSequence.actions(moveTo,
		// CCCallFunc.action(this, "moveToNext"));
		moveToNext();
	}

	private int current = 0;
	private int speed = 60;
	private CCParticleSystem system;

	// �ݹ�Ĳ���
	public void moveToNext() {
		current++;
		if (current < roads.size()) {
			// �����ƶ���ʱ�䣨���٣�
			float t = CGPointUtil.distance(sprite.getPosition(),
					roads.get(current))
					/ speed;
			CCMoveTo moveTo = CCMoveTo.action(t, roads.get(current));
			CCSequence sequence = CCSequence.actions(moveTo,
					CCCallFunc.action(this, "moveToNext"));
			sprite.runAction(sequence);
		} else {
			// current = 0;
			// CCPlace place = CCPlace.action(roads.get(current));
			// CCSequence sequence = CCSequence.actions(place,
			// CCCallFunc.action(this, "moveToNext"));
			// sprite.runAction(sequence);
			// ���еĶ���ȫ��ֹͣ
			system.stopSystem();// ѩ��Ҳֹͣ��
			sprite.stopAllActions();
			SoundEngine.sharedEngine().playSound(CCDirector.theApp,
					R.raw.start, false);
		}
	}

	private CCSprite getSprite() {
		CCSprite sprite = CCSprite.sprite("z_1_attack_01.png");
		sprite.setScale(.5f);
		sprite.setFlipX(true);// x�᾵��
		// ���ڵ�ͼ�Ͼͻ���ŵ�ͼ���ƶ����ƶ�
		gameMap.addChild(sprite);
		return sprite;
	}

	private void loadRoad() {
		roads = new ArrayList<CGPoint>();
		// gameMap
		// ��ȡname=road �����ObjectGroup
		CCTMXObjectGroup objectGroup = gameMap.objectGroupNamed("road");
		ArrayList<HashMap<String, String>> objects = objectGroup.objects;
		for (HashMap<String, String> item : objects) {
			int x = Integer.parseInt(item.get("x"));
			int y = Integer.parseInt(item.get("y"));
			CGPoint point = CGPoint.ccp(x, y);
			roads.add(point);
		}
		// ��ȡ��ObjectGroup���е�Object
	}

	private void loadMap() {
		gameMap = CCTMXTiledMap.tiledMap("map.tmx");
		// �����Ҫ�ƶ���ͼ�����뽫ê�����������ĵ��ϣ��޸ĵ�ͼ���ĵ������
		gameMap.setAnchorPoint(.5f, .5f);
		CGSize size = gameMap.getContentSize();
		gameMap.setPosition(size.width / 2, size.height / 2);
		this.addChild(gameMap);
	}

	public class PauseLayer extends CCLayer {
		private CCSprite sprite;

		public PauseLayer() {

			this.setIsTouchEnabled(true);
			sprite = getSprite();
			CGSize winSize = CCDirector.sharedDirector().getWinSize();
			sprite.setPosition(winSize.width / 2, winSize.height / 2);
		}

		@Override
		public boolean ccTouchesBegan(MotionEvent event) {
			// ������Ϸ
			CGPoint touchPos = this.convertTouchToNodeSpace(event);
			if (CGRect.containsPoint(sprite.getBoundingBox(), touchPos)) {
				// ��Ϸ����
				// ���ٵ�ǰlayer
				removeSelf();
//				this.setVisible(false);
				// new DemoLayer().onEnter();//����һ���㲥��������ô��
				System.out.println("===========");

				DemoLayer.this.onEnter();// ��Ϸ����
			}
			return super.ccTouchesBegan(event);
		}

		private CCSprite getSprite() {
			CCSprite sprite = CCSprite.sprite("z_1_attack_01.png");
			sprite.setAnchorPoint(0, 0);// ����ê��
			this.addChild(sprite);
			return sprite;
		}
	}
}
