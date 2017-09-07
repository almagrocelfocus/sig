package com.eai.common.ui.command;

import java.util.List;

import com.eai.common.exception.MethodNotImplemented;
import com.eai.common.ui.entity.UIPresentationObject;

public class AsyncEditPanelAdapter<T extends UIPresentationObject> {
	public void onDelete(T result){
		throw new MethodNotImplemented();
	}
	public void onDelete(List<T> result){
		throw new MethodNotImplemented();
	}
	public void onAdd(T result){
		throw new MethodNotImplemented();
	}
	public void onAdd(List<T> result){
		throw new MethodNotImplemented();
	}
	public void onEdit(T result){
		throw new MethodNotImplemented();
	}
}
