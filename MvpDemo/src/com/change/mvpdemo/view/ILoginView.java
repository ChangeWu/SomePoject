package com.change.mvpdemo.view;

/**
 * MVP模式的V（视图）层
 * 这是一个抽象的登陆视图，里面都是一些界面动作，想要执行这些动作的界面都会去实现它。
 * @author Change
 *
 */
public interface ILoginView extends IView{
	/**
	 * 弹出提示信息。
	 */
	public void showMsg(String msg);
	/**
	 * 成功登陆跳转主页。
	 */
	public void moveToMain();
	/**
	 * 加载中，万恶的菊花。
	 */
	public void showLoadding();
	/**
	 * 隐藏菊花。
	 */
	public void hideLoadding();
}
