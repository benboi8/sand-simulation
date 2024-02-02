package materials;

public enum MaterialProperties {
    /**
     * Display Name
     * <p>
     * - string
     */
    name,

    /**
     * List of how the material moves
     * <p>
     * - powder
     * <p>
     * - liquid
     * <p>
     * - random
     * <p>
     * - gas
     */
    movement,

    /**
     * mass of material
     * <p>
     * - int
     * <p>
     * - higher will displace other materials
     * <p>
     * - negative will go up
     */

    mass,

    /**
     * color
     * <p>
     * - string
     */
    color,

    /**
     * What is the material id
     * <p>
     * - string
     * */
    material,

    /**
     * lifespan of material
     * <p>
     * - double
     * <p>
     * - higher values last longer
     */
    life_span,

    /**
     * list of materials that get created on ignition
     */
    ignition_products,

    /**
    what is the fire value. higher is hotter
     <p>
     - int
     <p>
    - higher means it can set more things on fire and quicker,
     <p>
    - negative gets set to 0,
     <p>
    - if absent will be set to 0
     */
    fire_strength,
    /**
    how much can the material resit fire
     <p>
     - int
     <p>
    - higher can resit fire better,
     <p>
    - negative instantly combusts,
     <p>
    - if absent will be 0 but won't be flammable
     */
    fire_resistance,
    /**
     * what is the corrosive.
     * <p>
     * - int
     * <p>
     * - higher value is more corrosive
     * <p>
     * - negative value is set to 0
     * <p>
     * - if absent value is set to 0
     */
    corrosion_strength,
    /**
     *  how much can the material resit acid
     *  <p>
     *  - int
     *  <p>
     *  - higher can resit acid better,
     *  <p>
     *  - negative value is set to 0,
     *  <p>
     *  - if absent will be 0 but won't be corrodible
     */
    corrosion_resistance,
    /**
     * can be moved
     * <p>
     * - boolean
     */
    displaceable,

}