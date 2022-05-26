import React, { useEffect } from "react";

import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { Login } from "../../Components/LoginForm/LoginForm";
import { RootState } from "../../Store";

import "./LoginPage.css";

export const LoginPage: React.FC = () => {

    const userState = useSelector((state:RootState) => state.user);

    const navigator = useNavigate();

    useEffect(() => {
        if (!userState.error && userState.user) {
            navigator('/homepage');
        }
    }, [userState])

    return(
        <div className="login-page">
            <Login />
        </div>
    )

}