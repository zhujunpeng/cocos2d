package com.example.cocos2dtest;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	
	private CCDirector director;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CCGLSurfaceView surfaceView = new CCGLSurfaceView(this);
        setContentView(surfaceView);
        // ���һ������
        director = CCDirector.sharedDirector();
        // ���ķ���һ�������߳���surfaceview��ɻ��ư�
        director.attachInView(surfaceView);
        
        // ��ʾ֡��
        director.setDisplayFPS(true);
        // ��Ļ�ķ�������
        director.setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);
        director.setScreenSize(480, 320);
        
        CCScene sence = createSence();
        // ���ķ�����������������
        director.runWithScene(sence);
       
    }

	private CCScene createSence() {
		CCScene root = CCScene.node();
//		FirstLayer layer = new FirstLayer();
//		ActionLayer layer = new ActionLayer();
		DemoLayer layer = new DemoLayer();
		root.addChild(layer);
		return root;
	}

	@Override
	protected void onResume() {
		super.onResume();
		director.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		director.onPause();
	}

    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	director.end();
    }
}
