/**
 */
package org.nasdanika.demos.graph.compute;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Operator</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.nasdanika.demos.graph.compute.Operator#getOperands <em>Operands</em>}</li>
 * </ul>
 *
 * @see org.nasdanika.demos.graph.compute.ComputePackage#getOperator()
 * @model
 * @generated
 */
public interface Operator extends Operand {
	/**
	 * Returns the value of the '<em><b>Operands</b></em>' containment reference list.
	 * The list contents are of type {@link org.nasdanika.demos.graph.compute.Operand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operands</em>' containment reference list.
	 * @see org.nasdanika.demos.graph.compute.ComputePackage#getOperator_Operands()
	 * @model containment="true"
	 * @generated
	 */
	EList<Operand> getOperands();

} // Operator
