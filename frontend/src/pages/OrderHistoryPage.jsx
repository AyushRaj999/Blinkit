import { useEffect, useState } from 'react';
import api from '../services/api';

export default function OrderHistoryPage() {
  const [orders, setOrders] = useState([]);
  useEffect(() => { api.get('/api/orders/my').then((r) => setOrders(r.data)); }, []);
  return <div className="max-w-4xl mx-auto p-4"><h2 className="text-2xl font-bold">My Orders</h2>{orders.map((o) => <div key={o.id} className="border p-3 rounded bg-white my-2">#{o.id} - {o.status} - ₹{o.totalPrice}</div>)}</div>;
}
