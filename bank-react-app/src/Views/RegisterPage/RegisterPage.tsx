import React, { useEffect } from "react";
import { Navbar } from "../../Components/Navbar/Navbar";
import { RegisterForm } from "../../Components/RegisterForm/RegisterForm";

import "./RegisterPage.css"

export const RegisterPage: React.FC = () => {

    return (
        <div className="register-page">
            <div className="logo-header">
                <img src="/revature-logo.webp"/>
            </div>
            <br/>
            <h1>Register Form</h1>
            <br/>
            <RegisterForm/>
        </div>
    )
}