import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { Type } from "../../Interfaces/Type";
import { logoutUser } from "../../Slices/UserSlice";
import { AppDispatch, RootState } from "../../Store";
import { CustomerNavbar } from "./CustomerNavbar";
import { ManagerNavbar } from "./ManagerNavbar";

import './Navbar.css';

export const Navbar: React.FC = () => {
    
    const dispatch:AppDispatch = useDispatch();
    const navigator = useNavigate();

    const profile = useSelector((state:RootState) => state.user);

    useEffect(() => {
        if (!profile.user) {
            navigator("/login");
        }
    }, []);

    if (profile.user?.type.toString() == "MANAGER") {
        return (<ManagerNavbar/>);
    }
    else {
        return (<CustomerNavbar/>);
    }

}