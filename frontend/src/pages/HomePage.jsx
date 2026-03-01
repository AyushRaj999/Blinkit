import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import SearchBar from '../components/SearchBar';
import ProductCard from '../components/ProductCard';
import { fetchProducts } from '../redux/slices/productSlice';
import api from '../services/api';

export default function HomePage() {
  const [query, setQuery] = useState('');
  const dispatch = useDispatch();
  const { items, loading } = useSelector((s) => s.products);

  useEffect(() => { dispatch(fetchProducts(query)); }, [dispatch, query]);

  const addToCart = async (productId) => {
    await api.post('/api/cart/add', { productId, quantity: 1 });
    alert('Added to cart');
  };

  return (
    <main className="max-w-7xl mx-auto p-4 space-y-4">
      <h1 className="text-2xl font-bold">Delivering groceries in minutes</h1>
      <SearchBar value={query} onChange={setQuery} />
      {loading ? <p>Loading...</p> : (
        <section className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
          {items.map((p) => <ProductCard key={p.id} product={p} onAdd={addToCart} />)}
        </section>
      )}
    </main>
  );
}
