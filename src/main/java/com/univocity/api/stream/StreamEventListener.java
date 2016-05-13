package com.univocity.api.stream;

public abstract class StreamEventListener {

	public void initialize(){};
	public void onEventGroupStarted(String target){};
	public void onEventGroupClosed(String target){};
	public void onEventGroupAborted(String target){};
	public void onInsert(String target, Object[] newRow, StreamingContext context){};
	public void onUpdate(String target, Object[] originalRow, Object[] updatedRow, StreamingContext context){};
	public void onDelete(String target, Object[] deletedRow, StreamingContext context){};
	public void onCommand(String target, String command, StreamingContext context){};
	public void onError(String target,Exception error){};
	public void onOther(String target, Object unknownEvent, StreamingContext context){};
	public void cleanup(){};

}
