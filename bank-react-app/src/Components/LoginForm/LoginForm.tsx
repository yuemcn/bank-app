import React, {useState} from "react";
import { useDispatch } from "react-redux";
import { Link } from "react-router-dom";
import { loginUser, toggleError } from "../../Slices/UserSlice";
import { AppDispatch } from "../../Store";

import "./LoginForm.css";

export const Login: React.FC = () => {

    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    const dispatch: AppDispatch = useDispatch();

    const handleInput = (event:React.ChangeEvent<HTMLInputElement>) => {
        if (event.target.name === "username") {
            setUsername(event.target.value);
        }
        else {
            setPassword(event.target.value);
        }
    }

    const handleLogin = (event:React.MouseEvent<HTMLButtonElement>) => {
        let credentials = {
            username,
            password
        };

        dispatch(loginUser(credentials));
    }

    return(
        <div className="login">
            <div className="logo">
                <img src="/revBackground.png"/>
            </div>
            <br/>
            <div className="text-container">
                <form className="login-form">
                    <div className="input-div">
                        <h4 className="input-h4">Username</h4>
                        <input autoComplete="off" className="login-input" type="text" placeholder="username" name="username" onChange={handleInput}/>
                    </div>
                    <div className="input-div">
                        <h4 className="input-h4">Password</h4>
                        <input className="login-input" type="password" name="password" placeholder="password" onChange={handleInput}/>
                    </div>
                </form>
                <button className="login-button" onClick={handleLogin}>Login</button>
                <Link to="/register">New user? Register here</Link>
            </div>
        </div>
    )

}