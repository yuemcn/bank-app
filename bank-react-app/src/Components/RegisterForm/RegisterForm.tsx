import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { Type } from "../../Interfaces/Type";
import { registerUser } from "../../Slices/UserSlice";
import { AppDispatch, RootState } from "../../Store";

import "./RegisterForm.css";

export const RegisterForm: React.FC = () => {

    const userState = useSelector((state: RootState) => state.user);

    const [firstname, setFirstname] = useState<string>("");
    const [lastname, setLastname] = useState<string>("");
    const [ssn, setSSN] = useState<number | any>(0);
    const [email, setEmail] = useState<string>("");
    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    /*
    const [firstname, setFirstname] = useState<string | any>("");
    const [lastname, setLastname] = useState<string | any>("");
    const [ssn, setSSN] = useState<number | any>();
    const [email, setEmail] = useState<string | any>("");
    const [username, setUsername] = useState<string | any>("");
    const [password, setPassword] = useState<string | any>("");
    const type: Type = Type.CUSTOMER;
    */

    const navigator = useNavigate();
    const dispatch: AppDispatch = useDispatch();

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        if (event.target.name == "first-name-field") {
            setFirstname(event.target.value);
        }
        else if (event.target.name == "last-name-field") {
            setLastname(event.target.value);
        }
        else if (event.target.name == "ssn-field") {
            setSSN(event.target.value);
        }
        else if (event.target.name == "email-field") {
            setEmail(event.target.value);
        }
        else if (event.target.name == "username-field") {
            setUsername(event.target.value);
        }
        else if (event.target.name == "password-field") {
            setPassword(event.target.value);
        }
    }

    const handleSubmit = (event: React.FormEvent) => {
        event.preventDefault();
        let accountDetails = {
            firstname: firstname,
            lastname: lastname,
            ssn: ssn,
            email: email,
            username: username,
            password: password,
            type: Type.CUSTOMER
        }
        dispatch(registerUser(accountDetails));
        alert("New Customer Regsitered");
        navigator("/");
    }

    return (
        <div className="register">
            <form className="registraiton-form" onSubmit={handleSubmit}>
                <div className="input-div">
                    <h4 className="input-h4">First Name</h4>
                    <input className="registration-input" type="text" name="first-name-field" onChange={handleChange}/>
                </div>
                <div className="input-div">
                    <h4 className="input-h4">Last Name</h4>
                    <input className="registration-input" type="text" name="last-name-field" onChange={handleChange}/>
                </div>
                <div className="input-div">
                    <h4 className="input-h4">SSN</h4>
                    <input className="registration-input" type="password" name="ssn-field" onChange={handleChange}/>
                </div>
                <div className="input-div">
                    <h4 className="input-h4">Email</h4>
                    <input className="registration-input" type="text" name="email-field" onChange={handleChange}/>
                </div>
                <div className="input-div">
                    <h4 className="input-h4">Username</h4>
                    <input className="registration-input" type="text" name="username-field" onChange={handleChange}/>
                </div>
                <div className="input-div">
                    <h4 className="input-h4">Password</h4>
                    <input className="registration-input" type="password" name="password-field" onChange={handleChange}/>
                </div>
            </form>
            <br/>
            <button className="submit-button" onClick={handleSubmit}>Submit</button>
            <Link to="/">Return to Login</Link>

        </div>
    )

    /*
    return (
        <form className="registration-form" onSubmit={handleSubmit}>
            <h3>First name: <span><input type="text" className="first-name-field" onChange={handleChange}></input></span></h3>
            <h3>Last name: <span><input type="text" className="last-name-field" onChange={handleChange}></input></span></h3>
            <h3>SSN: <span><input type="password" className="ssn-name-field" onChange={handleChange}></input></span></h3>
            <h3>Email: <span><input type="text" className="email-name-field" onChange={handleChange}></input></span></h3>
            <h3>Username: <span><input type="text" className="username-name-field" onChange={handleChange}></input></span></h3>
            <h3>Password: <span><input type="password" className="password-name-field" onChange={handleChange}></input></span></h3>
            
            <input type="submit" className="submit-button"></input>
            <br/>
            <button className="return-button" onClick={handleReturn}>Return to Login</button>
        </form>
    )
    */
}