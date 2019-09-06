package stateless;

import java.util.Collection;

/* Clase necesaria para la paginacion en el front-end */

public class Page<T> {
	private Integer current;
	private Integer count;
	private Integer prev;
	private Integer next;
	private Integer last;
	private Collection<T> results;

	public Page(Integer current, Integer count, Integer prev, Integer next, Integer last, Collection<T> results) {
		super();
		this.current = current;
		this.count = count;
		this.prev = prev;
		this.next = next;
		this.last = last;
		this.results = results;
	}

	public Integer getCurrent() {
		return current;
	}

	public void setCurrent(Integer current) {
		this.current = current;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getPrev() {
		return prev;
	}

	public void setPrev(Integer prev) {
		this.prev = prev;
	}

	public Integer getNext() {
		return next;
	}

	public void setNext(Integer next) {
		this.next = next;
	}

	public Integer getLast() {
		return last;
	}

	public void setLast(Integer last) {
		this.last = last;
	}

	public Collection<T> getResults() {
		return results;
	}

	public void setResults(Collection<T> results) {
		this.results = results;
	}
}
