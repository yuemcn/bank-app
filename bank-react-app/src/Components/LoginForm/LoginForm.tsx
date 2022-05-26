import React, {useState} from "react";
import { useDispatch } from "react-redux";
import { loginUser, toggleError } from "../../Slices/UserSlice";
import { AppDispatch } from "../../Store";

import "./LoginForm.css";

export const Login: React.FC = () => {

    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    const dispatch: AppDispatch = useDispatch();

    // const dispatch = useDispatch();

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
            <div className="logo-header">
                <img src="/revature-logo.webp"/>
            </div>
            <br/>
            <div className="text-container">
                <h1 className="login-header">Welcome to Revature Bank</h1>
                <h2 className="login-header">Sign in to use the online application</h2>
                <br/>
                <form className="login-form">
                    <div className="input-div">
                        <h4 className="input-h4">Please enter your username</h4>
                        <input autoComplete="off" className="login-input" type="text" placeholder="username" name="username" onChange={handleInput}/>
                    </div>
                    <br/>
                    <div className="input-div">
                        <h4 className="input-h4">Please enter your password</h4>
                        <input className="login-input" type="password" name="password" placeholder="password" onChange={handleInput}/>
                    </div>
                </form>
                <button className="login-button" onClick={handleLogin}>Login</button>
            </div>
        </div>
    )

}