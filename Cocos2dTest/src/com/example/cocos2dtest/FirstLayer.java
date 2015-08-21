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
		// ����layer���Դ����touch����
		this.setIsTouchEnabled(true);// ��һ�����������е�layer�У�����һ��layer�ܹ������û���touch
		// ͼƬ�����ꡢ���Լ�����ķ���
		CCSprite leaf = CCSprite.sprite("z_1_attack_01.png");
		this.addChild(leaf, 1);
		// ������Ϣ����
		Log.i(TAG, "x��" + leaf.getPosition().x + "y��" + leaf.getPosition().y);
		// Ϊʲô��ʾ��һ���֣�������ê�㣺������ͼƬ���е���
		leaf.setAnchorPoint(0, 0);
		// ͼƬλ�õ�ȷ��������ê�������ۺ϶�λ��ԭ���������򵥣�

		// ������
		flipX();
		flipY();
		// addchild��������ӵ�˳�������ʾ���ȼ�������ӵĸ���ǰ����ӵģ�
		// �����һ��������ʾ��addChild��child��z��,Z����Z��������Ĭ��ֵ0
		// addChild��child��z,tag��
	}

	private void flipX() {
		CCSprite sprite = CCSprite.sprite("z_1_attack_01.png");
		// ���þ���
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
		// ���þ���
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
		// Log.i(TAG, "x��" + event.getX() + "x��" + event.getY());
		// ������棬��ȡX�᾵�������
		CCSprite filpX = (CCSprite) getChildByTag(TAG_X);
		Log.i(TAG, "x��" + filpX.getPosition().x + "y��" + filpX.getPosition().y);
		// �жϾ���ĵ��
		// ��ͬMotionEvent-��Ļ������:cocos2d���������-openGl����ϵͳ��ԭ�㣩
		CGPoint touchPos = this.convertTouchToNodeSpace(event);
		System.out.println("----" + filpX.getBoundingBox() + "+++++++" + touchPos);
		// ����һ���������򣬲�����������������
		boolean containsPoint = CGRect.containsPoint(filpX.getBoundingBox(), touchPos);
		if (containsPoint) {
//			filpX.setOpacity(100);//͸����
			filpX.setVisible(false);//����
//			filpX.removeSelf();// ����
		}
		return super.ccTouchesBegan(event);
	}

}
