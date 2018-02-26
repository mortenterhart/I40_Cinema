package utility;

import main.Configuration;

public class EnumUtility {

    /**
     * Selects randomly an enum constant out of the given enum <code>enumType</code>
     * and returns it. Uses the random generator {@link random.MersenneTwisterFast}.
     *
     * @param enumType the class object holding the enumeration type
     * @param <E>      the type qualifier ensuring the class is a subclass of {@link Enum}&lt;E&gt;
     * @return the enumeration constant of type E
     */
    public static <E extends Enum<E>> E randomEnumConstant(Class<E> enumType) {
        int x = Configuration.instance.mersenneTwister.nextInt(enumType.getEnumConstants().length);
        return enumType.getEnumConstants()[x];
    }
}
