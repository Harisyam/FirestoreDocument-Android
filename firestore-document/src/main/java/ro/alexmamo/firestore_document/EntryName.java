package ro.alexmamo.firestore_document;

import static ro.alexmamo.firestore_document.utils.Constants.ADDITIONAL_BYTE;

class EntryName {
    int getSize(String entryName)  {
        return entryName.length() + ADDITIONAL_BYTE;
    }
}