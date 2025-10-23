import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

// 🟢 Componentes Públicos (Asumiendo que todos usan EXPORT DEFAULT)
import Home from './pages/Home/Home'; 
import Contacto from './pages/Contacto/Contacto'; 
import Inventario from './pages/Inventario/inventario'; 

// 🟢 Componentes de Formulario
import CrearProducto from './componentes/CrearProd/CrearProducto'; 
import EditarProd from './componentes/EditarProd/EditarProd'; 
import Productos from './componentes/Productos/Productos'; 

// 🟢 Componentes del Dashboard y Autenticación
import DashboardLayout from './pages/DashboardLayout.jsx'; 
import DashboardContent from './pages/DashboardContent.jsx'; 
import Login from './pages/Login.jsx'; 
import Registro from './pages/Registro.jsx'; // ⬅️ Nuevo componente
import GestionUsuarios from './pages/GestionUsuarios.jsx'; 

import './App.css'; 

function App() {
  return (
    <Router>
      <Routes>
        
        {/* ======================================= */}
        {/* 🟢 RUTAS PÚBLICAS Y DE TIENDA           */}
        {/* ======================================= */}
        <Route path="/" element={<Home />} />
        <Route path="/contacto" element={<Contacto />} />
        <Route path="/inventario" element={<Inventario />} />
        <Route path="/productos" element={<Productos />} />

        {/* 🟢 RUTAS DE AUTENTICACIÓN */}
        <Route path="/login" element={<Login />} />
        <Route path="/registro" element={<Registro />} />


        {/* ======================================= */}
        {/* 🟢 RUTAS ADMINISTRATIVAS (ANIDADAS)     */}
        {/* ======================================= */}
        <Route path="/admin" element={<DashboardLayout />}>
            
            {/* Rutas con el Layout del Administrador */}
            <Route index element={<DashboardContent />} />
            <Route path="dashboard" element={<DashboardContent />} />
            <Route path="usuarios" element={<GestionUsuarios />} />
            <Route path="productos" element={<Productos />} /> 
            <Route path="crear-producto" element={<CrearProducto />} /> 
            <Route path="editar-producto/:id" element={<EditarProd />} />
        </Route>

        {/* NOTA: Debes implementar un componente NotFound.jsx si quieres manejar errores 404 */}

      </Routes>
    </Router>
  );
}

export default App;