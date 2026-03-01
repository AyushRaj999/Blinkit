import { useEffect, useState } from 'react';
import api from '../services/api';

export default function ProfilePage() {
  const [profile, setProfile] = useState({});
  useEffect(() => { api.get('/api/users/profile').then((r) => setProfile(r.data)); }, []);
  return <div className="max-w-3xl mx-auto p-4"><h2 className="text-2xl font-bold">Profile</h2><p>{profile.name} ({profile.email})</p></div>;
}
