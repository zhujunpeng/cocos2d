package com.example.cocos2dtest;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import android.util.Log;
import android.view.MotionEvent;

public class FirstLayer extends CCLayer {

	private static final String TAG = "FirstLayer";
	private static final int TAG_X = 1;

	public FirstLayer() {
		// 设置layer可以处理的touch开关
		this.setIsTouchEnabled(true);// 在一个场景中所有的layer中，有且一个layer能够处理用户的touch
		// 图片、坐标、花自己处理的方法
		CCSprite leaf = CCSprite.sprite("z_1_attack_01.png");
		this.addChild(leaf, 1);
		// 坐标信息？？
		Log.i(TAG, "x：" + leaf.getPosition().x + "y：" + leaf.getPosition().y);
		// 为什么显示了一部分？？？，锚点：设置在图片的中点上
		leaf.setAnchorPoint(0, 0);
		// 图片位置的确定：采用锚点坐标综合定位（原则：坐标计算简单）

		// 处理镜像
		flipX();
		flipY();
		// addchild：按照添加的顺序决定显示优先级（后添加的覆盖前面添加的）
		// 如果第一个优先显示，addChild（child，z）,Z轴在Z进行排序，默认值0
		// addChild（child，z,tag）
	}

	private void flipX() {
		CCSprite sprite = CCSprite.sprite("z_1_attack_01.png");
		// 设置镜像
		sprite.setFlipX(true);
		sprite.setAnchorPoint(0, 0);

		// sprite.setPosition(100, 0);
		CGPoint pos = CGPoint.ccp(100, 0);
		sprite.setPosition(pos);

		sprite.setFlipX(true);
		
		this.addChild(sprite, 0, TAG_X);

	}

	private void flipY() {
		CCSprite sprite = CCSprite.sprite("z_1_attack_01.png");
		// 设置镜像
		sprite.setFlipX(true);
		this.addChild(sprite);
		sprite.setAnchorPoint(0, 0);

		// sprite.setPosition(0,100);
		CGPoint pos = CGPoint.ccp(0, 100);
		sprite.setPosition(pos);

		sprite.setFlipX(true);

	}

	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		// Log.i(TAG, "x：" + event.getX() + "x：" + event.getY());
		// 点击界面，获取X轴镜像的坐标
		CCSprite filpX = (CCSprite) getChildByTag(TAG_X);
		Log.i(TAG, "x：" + filpX.getPosition().x + "y：" + filpX.getPosition().y);
		// 判断精灵的点击
		// 不同MotionEvent-屏幕的坐标:cocos2d处理的坐标-openGl坐标系统（原点）
		CGPoint touchPos = this.convertTouchToNodeSpace(event);
		System.out.println("----" + filpX.getBoundingBox() + "+++++++" + touchPos);
		// 参数一：矩形区域，参数二：触摸的区域
		boolean containsPoint = CGRect.containsPoint(filpX.getBoundingBox(), touchPos);
		if (containsPoint) {
//			filpX.setOpacity(100);//透明度
			filpX.setVisible(false);//隐藏
//			filpX.removeSelf();// 销毁
		}
		return super.ccTouchesBegan(event);
	}

}
