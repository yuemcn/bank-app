import React, { useEffect } from "react";
import { Navbar } from "../../Components/Navbar/Navbar";
import { RegisterForm } from "../../Components/RegisterForm/RegisterForm";

import "./RegisterPage.css"

export const RegisterPage: React.FC = () => {

    return (
        <div className="register-page">
            <div className="logo-header">
                <img src="/revBackground.png"/>
            </div>
            <RegisterForm/>
        </div>
    )
}