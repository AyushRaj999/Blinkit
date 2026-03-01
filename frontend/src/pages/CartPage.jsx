import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import api from '../services/api';

export default function CartPage() {
  const [cart, setCart] = useState({ items: [] });
  useEffect(() => { api.get('/api/cart').then((r) => setCart(r.data)); }, []);

  return (
    <div className="max-w-4xl mx-auto p-4">
      <h2 className="text-2xl font-bold mb-4">Cart</h2>
      {cart.items.map((i) => <div key={i.id} className="bg-white border p-3 mb-2 rounded">{i.product.name} x {i.quantity}</div>)}
      <Link to="/checkout" className="bg-green-600 text-white px-4 py-2 rounded inline-block">Checkout</Link>
    </div>
  );
}
