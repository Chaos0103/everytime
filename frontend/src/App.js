import React from "react";
import {Route, BrowserRouter, Routes} from "react-router-dom";
import IndexPage from "./components/Index";
import LoginPage from "./components/Login";
import MainPage from "./components/Main";
import "./css/common.css"

function App() {

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<IndexPage/>}></Route>
                <Route path="/login" element={<LoginPage/>}></Route>
                <Route path="/main" element={<MainPage/>}></Route>
            </Routes>
        </BrowserRouter>
    );
}

export default App;
