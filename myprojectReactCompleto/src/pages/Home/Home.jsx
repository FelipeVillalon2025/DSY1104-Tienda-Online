import Navbar from "../../componentes/Navbar/Navbar";

// 🟢 CAMBIO: Usamos export default para que App.jsx lo reconozca
export default function Home(){ 
    
    return(
        <>
            <div className="container">
                <Navbar/>
            </div>
        </>
        
    )
}