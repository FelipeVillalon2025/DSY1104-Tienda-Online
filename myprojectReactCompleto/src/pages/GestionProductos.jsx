export default function GestionProductos() {
    return (
        <div className="container mt-4">
            <h2 className="mb-4">Gestión de Productos (CRUD)</h2>
            
            {/* Form for adding products */}
            <div className="mb-4">
                <h3>Agregar Producto</h3>
                <form>
                    <input type="text" className="form-control mb-2" placeholder="Nombre del producto" />
                    <input type="number" className="form-control mb-2" placeholder="Precio" />
                    <button type="submit" className="btn btn-primary">Agregar</button>
                </form>
            </div>

            {/* Product list */}
            <div>
                <h3>Lista de Productos</h3>
                <ul className="list-group">
                    <li className="list-group-item d-flex justify-content-between align-items-center">
                        Producto Ejemplo
                        <div>
                            <button className="btn btn-warning me-2">Editar</button>
                            <button className="btn btn-danger">Eliminar</button>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    );
}