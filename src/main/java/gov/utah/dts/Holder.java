package gov.utah.dts;

import java.util.concurrent.ExecutorService;

/**
 * Created by jjensen on 3/30/16.
 */
public class Holder {
    private ExecutorService executorService;
    private InteruptableRunnable runnable;

    public Holder(ExecutorService executorService, InteruptableRunnable job) {
        this.executorService = executorService;
        this.runnable = job;
    }


    public ExecutorService getExecutorService() {
        return this.executorService;
    }

    public InteruptableRunnable getRunnable() {
        return this.runnable;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void setRunnable(InteruptableRunnable runnable) {
        this.runnable = runnable;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Holder)) return false;
        final Holder other = (Holder) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$executorService = this.executorService;
        final Object other$executorService = other.executorService;
        if (this$executorService == null ? other$executorService != null : !this$executorService.equals(other$executorService))
            return false;
        final Object this$runnable = this.runnable;
        final Object other$runnable = other.runnable;
        if (this$runnable == null ? other$runnable != null : !this$runnable.equals(other$runnable)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $executorService = this.executorService;
        result = result * PRIME + ($executorService == null ? 0 : $executorService.hashCode());
        final Object $runnable = this.runnable;
        result = result * PRIME + ($runnable == null ? 0 : $runnable.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof Holder;
    }

    public String toString() {
        return "Holder(executorService=" + this.executorService + ", runnable=" + this.runnable + ")";
    }
}
