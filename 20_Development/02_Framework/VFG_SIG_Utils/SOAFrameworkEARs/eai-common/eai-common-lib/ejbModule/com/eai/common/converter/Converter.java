package com.eai.common.converter;

import java.util.ArrayList;
import java.util.List;

import com.eai.common.utils.ListUtils;

public abstract class Converter<S, T> {
	public abstract T convert(S source);

	public List<T> converts(List<S> sources) {

		List<T> result = new ArrayList<T>();
		if (!ListUtils.isNullOrEmpty(sources)) {

			for (S source : sources) {
				result.add(convert(source));
			}
		}

		return result;
	}

}
