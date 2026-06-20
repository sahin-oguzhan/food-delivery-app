import { useAuth } from '@/context/AuthContext';
import { useRouter } from 'next/navigation';
import { useEffect } from 'react';

export function useOwnerGuard() {
  const { user, loading } = useAuth();
  const router = useRouter();

  useEffect(() => {
    if (
      !loading &&
      (!user || (user.role !== 'ROLE_OWNER' && user.role !== 'ROLE_ADMIN'))
    ) {
      router.push('/');
    }
  }, [user, loading, router]);

  return { user, loading };
}
