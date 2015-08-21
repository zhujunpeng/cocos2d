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
        // 获得一个导演
        director = CCDirector.sharedDirector();
        // 核心方法一：开启线程在surfaceview完成绘制绑定
        director.attachInView(surfaceView);
        
        // 显示帧率
        director.setDisplayFPS(true);
        // 屏幕的方向设置
        director.setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);
        director.setScreenSize(480, 320);
        
        CCScene sence = createSence();
        // 核心方法二，场景跑起来
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
