package ck.swing1.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import ck.swing1.model.Message;
/**
 * Simulated MessageServer
 */
public class MessageServer implements Iterable<Message> {
	
	private Map<Integer, List<Message>> messages;
	private List<Message> selected;

	public MessageServer() {
		this.selected = new ArrayList<>();
		this.messages = new TreeMap<>();
		
		List<Message> list = new ArrayList<>();
		list.add(new Message("Cat is Missing", "Have you seen Felix anywhere?"));
		list.add(new Message("See you later?", "Are we still meeting in the pub?"));
		messages.put(0, list);
		list = new ArrayList<>();
		list.add(new Message("How about dinner later?", "Are you doing anything later on?"));
		messages.put(1, list);
	}
	
	public void setSelectedServers(Set<Integer> serverIds) {
		
		selected.clear();
		
		for (Integer id : serverIds) {
			if(messages.containsKey(id)) {
				selected.addAll(messages.get(id));
			}
		}
	}
	
	public int getMessageCount() {
		return selected.size();
	}

	@Override
	public Iterator iterator() {
		return new MessageIterator(selected);
	}

}

class MessageIterator implements Iterator {
	
	private Iterator<Message> iterator;
	
	public MessageIterator(List<Message> messages) {
		iterator = messages.iterator();
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public Object next() {
		try {
			Thread.sleep(500); //to demo slow processing so that gui is locked. multi-threading in next commit ;-)
		} catch (InterruptedException e) {
		}
		return iterator.next();
	}
	
}
