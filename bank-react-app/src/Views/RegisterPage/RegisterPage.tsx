import React from "react";
import { RegisterForm } from "../../Components/RegisterForm/RegisterForm";

import "./RegisterPage.css"

export const RegisterPage: React.FC = () => {

    return (
        <div className="register-page">
            <div className="logo-header">
                <img src="/revBackground.png" alt="revature logo"/>
            </div>
            <RegisterForm/>
        </div>
    )
}