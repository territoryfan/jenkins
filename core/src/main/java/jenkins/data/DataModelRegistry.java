package jenkins.data;

import hudson.model.Describable;
import jenkins.model.Jenkins;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Set;

/**
 * A Registry to allow {@link DataModel}s retrieval.
 *
 * @author <a href="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
public interface DataModelRegistry {
    /**
     * Retrieve a {@link DataModel} for target type.
     * @param type
     * @return <code>null</code> if we don't know any {@link DataModel} for requested type
     */
    @CheckForNull
    <T> DataModel<T> lookup(Class<T> type);

    /**
     * null-safe flavour of {@link #lookup(Class)}.
     * @param type
     * @throws IllegalArgumentException if we don't know any {@link DataModel} for requested type
     */
    @Nonnull
    default <T> DataModel<T> lookupOrFail(Class<T> type) {
        DataModel<T> t = lookup(type);
        if (t==null)    throw new IllegalArgumentException("No DataModel found for "+type);
        return t;
    }

    /**
     * Finds all the valid databindable subtypes assignable to given type.
     */
     // TODO: the return type not being Set<DataModel<?>> makes me feel uneasy
    <T extends Describable> Set<Class> findSubtypes(Class<T> superType);

    /**
     * Retrieve default implementation from Jenkins
     */
    static DataModelRegistry get() {
        return Jenkins.get().getExtensionList(DataModelRegistry.class).get(0);
    }
}
