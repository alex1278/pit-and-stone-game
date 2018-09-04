package engine.utility;

import org.hashids.Hashids;

import java.util.HashMap;
import java.util.Map;

public class IdentityCreator {

    private static volatile IdentityCreator instance;

    private long counter = 1L;
    private Map<String, Boolean> usedHashes = new HashMap<>();

    private IdentityCreator(){
        if (instance != null){
            throw new RuntimeException("Use use the IdentityCreator, access the singleton via the get() method.");
        }
    }

    public static IdentityCreator get() {
        if(instance == null) {
            synchronized (IdentityCreator.class) {
                if(instance == null) instance = new IdentityCreator();
            }
        }
        return instance;
    }

    public synchronized String newId(int length) {
        return createId(length);
    }

    private String createId(int length) {
        String hash = makeHash(length);
        while (usedHashes.containsKey(hash)) {
            hash = makeHash(length);
        }
        usedHashes.put(hash, true);
        return hash;
    }

    private String makeHash(int length) {
        return new Hashids("bol.game", length)
                .encode(++counter)
                .toUpperCase()
                .substring(0, length);
    }

}
