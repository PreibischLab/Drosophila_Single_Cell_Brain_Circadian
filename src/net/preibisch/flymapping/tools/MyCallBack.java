package net.preibisch.flymapping.tools;

public interface MyCallBack {
	void onSuccess();
	void onError(String error);
	void log(String log);
}

