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
 * 主要的layer
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
		// 1、在一个九曲十八弯的小路上
		// 提供的题图
		// a,细节处理，图片资源相对路径
		// b 名称的定义，不能有中文
		// c 对象层存储数据的顺序（处理道路）
		loadMap();
		// 解析加工的地图-解析的结果存储的位置
		loadRoad();
		// 获取到道路的拐弯点
		// 2、一个僵尸冒着大雪前行
		// 前行
		move();
		// 序列帧的播放
		animation();
		// 飘雪场景处理：粒子系统
		particleSystem();
		// 3、最后雪停了
		// 粒子系统控制

		// 4、僵尸高兴的跳起了舞
		// 处理声音引擎
		// 1、音乐，2、音效
		// 播放+暂停+停止，
		// MediaPlayer ;SoundPool
		// 音量的控制
		// 辅助内容：移动地图处理
		// 大地图与小地图的一定
		// CCFollow
		// 大地图的移动，英雄在屏幕中间，会出现黑边
		// this.runAction(CCFollow.action(sprite));
		// 小地图
		// 游戏暂停的操作
	}

	@Override
	public boolean ccTouchesMoved(MotionEvent event) {
		gameMap.touchMove(event, gameMap);

		return super.ccTouchesMoved(event);
	}

	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		// 显示暂停的layer
		onExit();
		// 添加暂停的layer，必须添加到场景（CCSense）中
		PauseLayer layer = new PauseLayer();
		getParent().addChild(layer);

		return super.ccTouchesBegan(event);
	}

	/**
	 * 粒子系统
	 */
	private void particleSystem() {
		system = CCParticleSnow.node();
		// 设置图片资源
		system.setTexture(CCTextureCache.sharedTextureCache().addImage("f.png"));
		// 添加粒子系统到layer中
		this.addChild(system);
	}

	private void animation() {
		// 一组有序的图片
		// 设置时间间隔
		ArrayList<CCSpriteFrame> frames = new ArrayList<CCSpriteFrame>();
		// String fileName = "z_1_0%d.png";//1-9
		String fileName = "z_1_%02d.png";// (1-99)
		for (int i = 1; i <= 7; i++) {
			CCSpriteFrame frame = CCSprite.sprite(String.format(fileName, i))
					.displayedFrame();
			frames.add(frame);
		}
		CCAnimation animation = CCAnimation.animation("", 0.2f, frames);
		CCAnimate animate = CCAnimate.action(animation);// 不停地播放
		CCRepeatForever forever = CCRepeatForever.action(animate);
		sprite.runAction(forever);
	}

	/**
	 * 移动
	 */
	private void move() {
		sprite = getSprite();
		sprite.setPosition(roads.get(0));
		// sprite.runAction(moveTo);

		// for (int i = 0; i < roads.size(); i++) {
		// CCMoveTo moveTo = CCMoveTo.action(2, roads.get(1));
		// sprite.runAction(moveTo);
		// }
		// 一个
		// CCSequence sequence = CCSequence.actions(moveTo,
		// CCCallFunc.action(this, "moveToNext"));
		moveToNext();
	}

	private int current = 0;
	private int speed = 60;
	private CCParticleSystem system;

	// 递归的操作
	public void moveToNext() {
		current++;
		if (current < roads.size()) {
			// 计算移动的时间（匀速）
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
			// 所有的动作全部停止
			system.stopSystem();// 雪花也停止掉
			sprite.stopAllActions();
			SoundEngine.sharedEngine().playSound(CCDirector.theApp,
					R.raw.start, false);
		}
	}

	private CCSprite getSprite() {
		CCSprite sprite = CCSprite.sprite("z_1_attack_01.png");
		sprite.setScale(.5f);
		sprite.setFlipX(true);// x轴镜像
		// 绑定在地图上就会跟着地图的移动而移动
		gameMap.addChild(sprite);
		return sprite;
	}

	private void loadRoad() {
		roads = new ArrayList<CGPoint>();
		// gameMap
		// 获取name=road 对象层ObjectGroup
		CCTMXObjectGroup objectGroup = gameMap.objectGroupNamed("road");
		ArrayList<HashMap<String, String>> objects = objectGroup.objects;
		for (HashMap<String, String> item : objects) {
			int x = Integer.parseInt(item.get("x"));
			int y = Integer.parseInt(item.get("y"));
			CGPoint point = CGPoint.ccp(x, y);
			roads.add(point);
		}
		// 获取到ObjectGroup所有的Object
	}

	private void loadMap() {
		gameMap = CCTMXTiledMap.tiledMap("map.tmx");
		// 如果需要移动地图，必须将锚点设置在中心点上，修改地图中心点的坐标
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
			// 继续游戏
			CGPoint touchPos = this.convertTouchToNodeSpace(event);
			if (CGRect.containsPoint(sprite.getBoundingBox(), touchPos)) {
				// 游戏继续
				// 销毁当前layer
				removeSelf();
//				this.setVisible(false);
				// new DemoLayer().onEnter();//发送一个广播？还是怎么的
				System.out.println("===========");

				DemoLayer.this.onEnter();// 游戏继续
			}
			return super.ccTouchesBegan(event);
		}

		private CCSprite getSprite() {
			CCSprite sprite = CCSprite.sprite("z_1_attack_01.png");
			sprite.setAnchorPoint(0, 0);// 设置锚点
			this.addChild(sprite);
			return sprite;
		}
	}
}
