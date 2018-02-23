package za.co.zantech.utils;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Consumer;

/**
 * Created by zandrewitte on 2017/05/17.
 * CompletableFutureUtils
 */
public interface CompleteAsync {

    static <T> CompletableFuture<T> runParallel(List<? extends CompletionStage<? extends T>> l) {

        CompletableFuture<T> f=new CompletableFuture<>();
        Consumer<T> complete=f::complete;
        CompletableFuture.allOf(
                l.stream().map(s -> s.thenAccept(complete)).toArray(CompletableFuture<?>[]::new)
        ).exceptionally(ex -> { f.completeExceptionally(ex); return null; });

        return f;
    }

}
