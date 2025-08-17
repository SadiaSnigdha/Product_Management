package com.example.product_management.DpApply;

import java.util.HashMap;
import java.util.Map;

public class DraftManager {
    private final Map<String, CartMemento> drafts = new HashMap<>();

    public void saveDraft(String customerPhone, CartMemento memento) {
        drafts.put(customerPhone, memento);
    }

    public boolean hasDraft(String customerPhone) {
        return drafts.containsKey(customerPhone);
    }

    public CartMemento getDraft(String customerPhone) {
        return drafts.get(customerPhone);
    }

    public void clearDraft(String customerPhone) {
        drafts.remove(customerPhone);
    }
}
