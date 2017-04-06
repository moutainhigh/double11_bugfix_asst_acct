package com.yuwang.pinju.core.filter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.yuwang.pinju.core.util.EmptyUtil;

public class FilterResult implements Iterable<FilterResult.FilterError> {

	private String statement;
	private List<FilterError> errors;

	public FilterResult(String statement) {
		this.statement = statement;
	}

	public boolean isSuccess() {
		return EmptyUtil.isBlank(errors);
	}

	public String getStatement() {
		return statement;
	}

	public void addError(int offset, String word) {
		addError(new FilterError(offset, word));
	}

	public void addError(FilterError filterError) {
		if (errors == null) {
			errors = new LinkedList<FilterResult.FilterError>();
		}
		errors.add(filterError);
	}

	public int errorsCount() {
		if (EmptyUtil.isBlank(errors)) {
			return 0;
		}
		return errors.size();
	}

	@Override
	public Iterator<FilterError> iterator() {
		return new FilterErrorIterator(errors);
	}

	public static class FilterError {
		private int offset;
		private String word;

		public FilterError(int offset, String word) {
			this.offset = offset;
			this.word = word;
		}

		public int getOffset() {
			return offset;
		}
		public String getWord() {
			return word;
		}

		@Override
		public String toString() {
			return "ItemResult [offset=" + offset + ", word=" + word + "]";
		}
	}

	private static class FilterErrorIterator implements Iterator<FilterError> {

		private Iterator<FilterError> errorIterator;

		private FilterErrorIterator(List<FilterError> errors) {
			if (errors != null) {
				errorIterator = errors.iterator();
			}
		}

		@Override
		public boolean hasNext() {
			return (errorIterator == null) ? false : errorIterator.hasNext();
		}

		@Override
		public FilterError next() {
			return (errorIterator == null) ? null : errorIterator.next();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("cannot execute remove() operation");
		}
	}
}
