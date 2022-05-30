import React from 'react';
import { HashRouter, Navigate, Route, Routes } from 'react-router-dom';
import './App.css';
import { RegisterForm } from './Components/RegisterForm/RegisterForm';
import { AccountsPage } from './Views/AccountsPage/AccountsPage';
import { CustomersPage } from './Views/CustomersPage/CustomersPage';
import { Homepage } from './Views/Homepage/Homepage';
import {LoginPage} from './Views/LoginPage/LoginPage';
import { ProfilePage } from './Views/ProfilePage/ProfilePage';
import { RegisterPage } from './Views/RegisterPage/RegisterPage';

function App() {
  return (
    <HashRouter>
      <Routes >
        <Route path="*" element={<Navigate to="/login" replace />}/>
        <Route path="/login" element={<LoginPage />}/>
        <Route path="/homepage" element={<Homepage />}/>
        <Route path="/profile" element={<ProfilePage />}/>
        <Route path="/accounts" element={<AccountsPage />}/>
        <Route path="/register" element={<RegisterPage />}/>
        <Route path="/customers" element={<CustomersPage/>}/>
      </Routes>
    </HashRouter>
  );
}

export default App;
