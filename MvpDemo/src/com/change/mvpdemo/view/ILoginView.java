package com.change.mvpdemo.view;

/**
 * MVPģʽ��V����ͼ����
 * ����һ������ĵ�½��ͼ�����涼��һЩ���涯������Ҫִ����Щ�����Ľ��涼��ȥʵ������
 * @author Change
 *
 */
public interface ILoginView extends IView{
	/**
	 * ������ʾ��Ϣ��
	 */
	public void showMsg(String msg);
	/**
	 * �ɹ���½��ת��ҳ��
	 */
	public void moveToMain();
	/**
	 * �����У����ľջ���
	 */
	public void showLoadding();
	/**
	 * ���ؾջ���
	 */
	public void hideLoadding();
}
