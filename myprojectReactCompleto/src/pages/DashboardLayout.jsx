// src/pages/DashboardLayout.jsx
import React from 'react';
import { Link, Outlet } from 'react-router-dom';

// Componente Sidebar (Para el menú lateral)
const Sidebar = () => (
    <div className="d-flex flex-column flex-shrink-0 p-3 text-white bg-dark" style={{ width: '250px', minHeight: '100vh' }}>
        <Link to="/admin" className="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-white text-decoration-none">
            <span className="fs-4">Admin Tienda</span>
        </Link>
        <hr />
        <ul className="nav nav-pills flex-column mb-auto">
            <li className="nav-item">
                <Link to="/admin/dashboard" className="nav-link text-white" aria-current="page">
                    <i className="bi bi-house me-2"></i> {/* Icono de ejemplo (requiere Bootstrap Icons) */}
                    Dashboard
                </Link>
            </li>
            <li>
                <Link to="/admin/productos" className="nav-link text-white">
                    <i className="bi bi-box-seam me-2"></i>
                    Gestión Productos
                </Link>
            </li>
            <li>
                <Link to="/admin/usuarios" className="nav-link text-white">
                    <i className="bi bi-people me-2"></i>
                    Gestión Usuarios
                </Link>
            </li>
            <hr />
            <li>
                <Link to="/logout" className="nav-link text-white">
                    <i className="bi bi-box-arrow-right me-2"></i>
                    Cerrar Sesión
                </Link>
            </li>
        </ul>
    </div>
);

// Componente principal del Layout
const DashboardLayout = () => {
    return (
        <div className="d-flex">
            {/* Menú Lateral */}
            <Sidebar />

            {/* Contenido Principal y Navbar Superior */}
            <div className="flex-grow-1">
                {/* Navbar Superior para el título y el Logout si es necesario */}
                <nav className="navbar navbar-light bg-light border-bottom">
                    <div className="container-fluid">
                        <span className="navbar-brand mb-0 h1">Módulo Administrativo</span>
                    </div>
                </nav>
                
                {/* Área donde se renderizan las rutas anidadas (Outlet) */}
                <main className="p-4">
                    <Outlet /> 
                </main>
            </div>
        </div>
    );
};

export default DashboardLayout;